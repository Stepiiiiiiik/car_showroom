package ru.gorlov.presentation.mapper;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.gorlov.core.entity.BaseEntity;
import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.entity.UserEntity;
import ru.gorlov.presentation.dto.order.common.CommonOrderCreateRequestDto;
import ru.gorlov.presentation.dto.order.common.CommonOrderFindDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommonOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "storeManager", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CommonOrderEntity toCreateEntity(CommonOrderCreateRequestDto dto);

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
        source = "state",
        target = "stateId",
        qualifiedByName = "mapCommonOrderStateToId"
    )
    CommonOrderFindDto toFindDto(CommonOrderEntity order);

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

    @Named("mapCommonOrderStateToId")
    default Short mapCommonOrderStateToId(ru.gorlov.core.enums.CommonOrderState state) {
        if (state == null) {
            return null;
        }

        return state.getId();
    }
}
