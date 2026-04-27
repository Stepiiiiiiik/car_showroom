package ru.gorlov.presentation.dto.order.custom;

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
public class CustomOrderCreateResponseDto {

    @NotNull(message = "Order id is required")
    private UUID id;
}
