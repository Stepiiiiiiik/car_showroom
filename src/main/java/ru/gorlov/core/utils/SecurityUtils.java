package ru.gorlov.core.utils;

import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.gorlov.core.exceptions.EntityNotFoundException;
import ru.gorlov.data.postgres.CommonOrderRepository;
import ru.gorlov.data.postgres.CustomOrderRepository;

@RequiredArgsConstructor
@Component
public class SecurityUtils {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated");
        }

        return authentication.getName();
    }

    public boolean hasRole(String id, String roleName) {
        try {
            UserResource userResource = keycloak.realm(realm).users().get(id);

            List<RoleRepresentation> roles = userResource.roles().realmLevel().listEffective();

            return roles.stream()
                .anyMatch(role -> roleName.equals(role.getName()));
        } catch (NotFoundException e) {
            return false;
        }
    }
}
