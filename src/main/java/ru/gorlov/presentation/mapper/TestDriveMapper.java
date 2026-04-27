package ru.gorlov.presentation.mapper;

import java.util.UUID;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.gorlov.core.entity.BaseEntity;
import ru.gorlov.core.entity.TestDriveEntity;
import ru.gorlov.presentation.dto.testdrive.TestDriveCreateRequestDto;
import ru.gorlov.presentation.dto.testdrive.TestDriveFindDto;
import ru.gorlov.presentation.dto.testdrive.TestDriveUpdateDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestDriveMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TestDriveEntity toCreateEntity(TestDriveCreateRequestDto dto);

    TestDriveFindDto toFindDto(TestDriveEntity testDrive);

    @Mapping(
        source = "car",
        target = "carId",
        qualifiedByName = "mapEntityToUuid"
    )
    TestDriveUpdateDto toUpdateDto(TestDriveEntity testDrive);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "client", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateEntity(@MappingTarget TestDriveEntity testDrive, TestDriveUpdateDto dto);

    @Named("mapEntityToUuid")
    default <T extends BaseEntity> UUID mapEntityToUuid(T entity) {
        if (entity == null) {
            return null;
        }

        return entity.getId();
    }
}
