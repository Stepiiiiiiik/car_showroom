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
public class CommonOrderCreateResponseDto {

    @NotNull(message = "Order id is required")
    private UUID orderId;
}
