package ru.gorlov.presentation.dto.car;

import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gorlov.core.entity.valueObjects.CarPower;
import ru.gorlov.core.entity.valueObjects.EngineVolume;
import ru.gorlov.core.entity.valueObjects.Price;
import ru.gorlov.core.enums.BodyType;
import ru.gorlov.core.enums.DriveshaftType;
import ru.gorlov.core.enums.EngineType;
import ru.gorlov.core.enums.TransmissionType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarUpdateDto {

    @Size(max = 20)
    private String brand;

    @Size(max = 100)
    private String model;

    private Price price;

    private BodyType bodyType;

    private EngineType engineType;

    private CarPower power;

    private EngineVolume engineVolume;

    private TransmissionType transmissionType;

    private DriveshaftType driveshaftType;

    @Size(max = 20)
    private String color;

    private Boolean testDriveAllowed;

    private Set<UUID> defaultSpareParts;

    private Set<UUID> allowedSpareParts;
}
