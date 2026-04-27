package ru.gorlov.presentation.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.gorlov.core.entity.CarEntity;
import ru.gorlov.core.entity.SparePartEntity;
import ru.gorlov.presentation.dto.car.CarCreateRequestDto;
import ru.gorlov.presentation.dto.car.CarFindDto;
import ru.gorlov.presentation.dto.car.CarUpdateDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper {

    @Mapping(
        source = "defaultSpareParts",
        target = "defaultSpareParts",
        qualifiedByName = "mapSparePartsToUuids")
    @Mapping(
        source = "allowedSpareParts",
        target = "allowedSpareParts",
        qualifiedByName = "mapSparePartsToUuids")
    CarFindDto toFindDto(CarEntity car);

    @Mapping(target = "defaultSpareParts", ignore = true)
    @Mapping(target = "allowedSpareParts", ignore = true)
    CarEntity toCreateEntity(CarCreateRequestDto dto);

    @Mapping(source = "defaultSpareParts", target = "defaultSpareParts", qualifiedByName = "mapSparePartsToUuids")
    @Mapping(source = "allowedSpareParts", target = "allowedSpareParts", qualifiedByName = "mapSparePartsToUuids")
    CarUpdateDto toUpdateDto(CarEntity car);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "defaultSpareParts", ignore = true)
    @Mapping(target = "allowedSpareParts", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdatedEntity(@MappingTarget CarEntity car, CarUpdateDto dto);


    @Named("mapSparePartsToUuids")
    default Set<UUID> mapSparePartsToUuids(Set<SparePartEntity> spareParts) {
        if (spareParts == null) {
            return new HashSet<>();
        }
        return spareParts.stream()
            .map(SparePartEntity::getId)
            .collect(Collectors.toSet());
    }
}
