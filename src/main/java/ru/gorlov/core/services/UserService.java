package ru.gorlov.core.services;

import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.gorlov.core.entity.UserEntity;
import ru.gorlov.core.exceptions.BadAuthDataException;
import ru.gorlov.core.exceptions.UserWithSuchDataAlreadyExistsException;
import ru.gorlov.data.postgres.UserRepository;
import ru.gorlov.presentation.dto.user.UserRegisterRequestDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final Keycloak keycloak;
    private final UserRepository userRepository;

    @Value("${keycloak.realm}")
    private String realm;

    private static @NonNull UserRepresentation initUserRepresentation(
        UserRegisterRequestDto request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));
        return user;
    }

    public String registerUserWithInitialRole(UserRegisterRequestDto request, String role) {
        UserRepresentation user = initUserRepresentation(request);

        UsersResource usersResource = keycloak.realm(realm).users();
        String userId;
        try (Response response = usersResource.create(user)) {
            responseStatusValidation(response);
            userId = CreatedResponseUtil.getCreatedId(response);
        }

        try {
            assignRealmRole(userId, role);
            userRepository.save(new UserEntity(UUID.fromString(userId)));
            return userId;
        } catch (Exception e) {
            usersResource.get(userId).remove();
            throw new RuntimeException("Failed to complete user registration", e);
        }
    }

    public void assignRealmRole(String userId, String roleName) {
        RealmResource realmResource = keycloak.realm(realm);
        UserResource userResource = realmResource.users().get(userId);

        RoleRepresentation roleRepresentation = realmResource.roles().get(roleName)
            .toRepresentation();

        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    private void responseStatusValidation(Response response) {
        if (response.getStatus() == HttpStatus.CREATED.value()) {
            return;
        } else if (response.getStatus() == HttpStatus.CONFLICT.value()) {
            throw new UserWithSuchDataAlreadyExistsException(
                "User with such data is already exists. Please, try again");
        } else if (response.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            throw new BadAuthDataException("Auth data is incorrect.Please try again");
        } else {
            throw new RuntimeException("Internal server error");
        }
    }
}
