package ru.gorlov.presentation.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {

    @NotNull(message = "Username is required")
    @Size(max = 255)
    private String username;

    @NotNull(message = "First name is required")
    @Size(max = 255)
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(max = 255)
    private String lastName;

    @NotNull(message = "Email is required")
    @Size(max = 255)
    private String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password can not be blank")
    @Size(min = 8)
    private String password;
}
