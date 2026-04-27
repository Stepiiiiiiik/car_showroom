package ru.gorlov.presentation.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.gorlov.core.entity.SparePartEntity;
import ru.gorlov.presentation.dto.part.SparePartCreateRequestDto;
import ru.gorlov.presentation.dto.part.SparePartFindDto;
import ru.gorlov.presentation.dto.part.SparePartUpdateDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SparePartMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SparePartEntity toCreateEntity(SparePartCreateRequestDto dto);

    SparePartFindDto toFindDto(SparePartEntity sparePart);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdatedEntity(@MappingTarget SparePartEntity sparePart,
        SparePartUpdateDto dto);

    SparePartUpdateDto toUpdateDto(SparePartEntity sparePart);
}
