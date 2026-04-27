package ru.gorlov.presentation.dto.order.common;

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
public class CommonOrderCreateRequestDto {

    @NotNull(message = "Store manager id is required")
    private UUID storeManagerId;

    @NotNull(message = "Car id is required")
    private UUID carId;
}
