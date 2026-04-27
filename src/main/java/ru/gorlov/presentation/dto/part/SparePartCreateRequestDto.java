package ru.gorlov.presentation.dto.part;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gorlov.core.entity.valueObjects.PartPrice;
import ru.gorlov.core.enums.SparePartType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparePartCreateRequestDto {

    @NotNull(message = "Price is required")
    private PartPrice price;

    @NotNull(message = "Type is required")
    private SparePartType type;
}
