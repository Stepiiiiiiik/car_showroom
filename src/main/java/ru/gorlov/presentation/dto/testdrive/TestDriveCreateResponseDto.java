package ru.gorlov.presentation.dto.testdrive;

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
public class TestDriveCreateResponseDto {

    @NotNull(message = "Test drive id is required")
    private UUID id;
}
