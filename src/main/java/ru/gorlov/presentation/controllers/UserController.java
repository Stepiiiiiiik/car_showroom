package ru.gorlov.presentation.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gorlov.core.services.UserService;
import ru.gorlov.presentation.dto.user.UserAssignRoleDto;
import ru.gorlov.presentation.dto.user.UserRegisterRequestDto;
import ru.gorlov.presentation.dto.user.UserRegisterResponseDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register-user")
    // todo: add dto for response
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.registerUserWithInitialRole(request, "user"));

    }

    @PostMapping("/register-manager")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<UserRegisterResponseDto> registerManager(
        @Valid @RequestBody UserRegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new UserRegisterResponseDto(userService.registerUserWithInitialRole(request, "manager")));
    }

    @PostMapping("/register-warehouse-manager")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<UserRegisterResponseDto> registerWarehouseManager(
        @Valid @RequestBody UserRegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new UserRegisterResponseDto(userService.registerUserWithInitialRole(request, "warehouse_manager")));
    }

    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<UserRegisterResponseDto> registerAdmin(
        @Valid @RequestBody UserRegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new UserRegisterResponseDto(userService.registerUserWithInitialRole(request, "admin")));
    }

    @PatchMapping("/assign-role")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> assignRole(@Valid @RequestBody UserAssignRoleDto request) {
        userService.assignRealmRole(request.getId().toString(), request.getRole());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
