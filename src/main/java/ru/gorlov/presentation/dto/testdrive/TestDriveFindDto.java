package ru.gorlov.presentation.dto.testdrive;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestDriveFindDto {

    @NotNull(message = "Test drive id is required")
    private UUID id;

    @NotNull(message = "Car id is required")
    private UUID carId;

    @NotNull(message = "Client id is required")
    private UUID clientId;

    @NotNull(message = "Start is required")
    private LocalDateTime start;
}
