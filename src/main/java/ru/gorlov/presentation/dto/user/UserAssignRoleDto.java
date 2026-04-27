package ru.gorlov.presentation.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAssignRoleDto {

    @NotNull(message = "Id is required")
    private UUID id;

    @NotNull(message = "Role is required")
    @NotBlank(message = "Role can not be blank")
    private String role;
}
