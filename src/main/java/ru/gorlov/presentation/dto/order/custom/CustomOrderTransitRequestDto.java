package ru.gorlov.presentation.dto.order.custom;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gorlov.core.enums.CustomOrderState;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomOrderTransitRequestDto {

    @NotNull(message = "Id is required")
    private UUID id;

    @NotNull(message = "State is required")
    private CustomOrderState state;
}
