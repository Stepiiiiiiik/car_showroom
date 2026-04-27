package ru.gorlov.presentation.dto.part;

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
public class SparePartUpdateDto {

    private PartPrice price;

    private SparePartType type;
}
