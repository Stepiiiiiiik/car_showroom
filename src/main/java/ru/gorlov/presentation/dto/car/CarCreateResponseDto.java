package ru.gorlov.presentation.dto.car;

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
public class CarCreateResponseDto {

    @NotNull(message = "Id is required")
    private UUID id;
}
