package ru.gorlov.presentation.dto.testdrive;

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
public class TestDriveUpdateDto {

    private UUID carId;

    private LocalDateTime start;
}
