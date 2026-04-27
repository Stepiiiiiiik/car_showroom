package ru.gorlov.presentation.mapper;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.gorlov.core.entity.BaseEntity;
import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.entity.SparePartEntity;
import ru.gorlov.core.entity.UserEntity;
import ru.gorlov.presentation.dto.order.custom.CustomOrderCreateRequestDto;
import ru.gorlov.presentation.dto.order.custom.CustomOrderFindDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "storeManager", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "configuration", ignore = true)
    CustomOrderEntity toCreateEntity(CustomOrderCreateRequestDto dto);

    @Mapping(
        source = "client",
        target = "clientId",
        qualifiedByName = "mapUserEntityToUuid")
    @Mapping(
        source = "storeManager",
        target = "storeManagerId",
        qualifiedByName = "mapUserEntityToUuid")
    @Mapping(
        source = "car",
        target = "carId",
        qualifiedByName = "mapEntityToUuid"
    )
    @Mapping(
        source = "configuration",
        target = "configuration",
        qualifiedByName = "mapSparePartsToUuids")
    @Mapping(
        source = "state",
        target = "stateId",
        qualifiedByName = "mapCustomOrderStateToId"
    )
    CustomOrderFindDto toFindDto(CustomOrderEntity customOrder);

    @Named("mapEntityToUuid")
    default <T extends BaseEntity> UUID mapEntityToUuid(T entity) {
        if (entity == null) {
            return null;
        }

        return entity.getId();
    }

    @Named("mapUserEntityToUuid")
     default UUID mapUserEntityToUuid(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return entity.getId();
    }

    @Named("mapSparePartsToUuids")
    default Set<UUID> mapSparePartsToUuids(Set<SparePartEntity> spareParts) {
        if (spareParts == null) {
            return new HashSet<>();
        }
        return spareParts.stream()
            .map(SparePartEntity::getId)
            .collect(Collectors.toSet());
    }

    @Named("mapCustomOrderStateToId")
    default Short mapCustomOrderStateToId(ru.gorlov.core.enums.CustomOrderState state) {
        if (state == null) {
            return null;
        }

        return state.getId();
    }
}
