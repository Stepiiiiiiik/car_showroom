package ru.gorlov.presentation.dto.order.common;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gorlov.core.enums.CommonOrderState;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonOrderTransitRequestDto {

    @NotNull(message = "State is required")
    private CommonOrderState state;
}
