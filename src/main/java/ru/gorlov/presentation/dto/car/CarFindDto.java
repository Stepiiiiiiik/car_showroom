package ru.gorlov.presentation.dto.car;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
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
public class CarFindDto {

    @NotNull(message = "Id is required")
    private UUID id;

    @NotBlank(message = "Brand is required")
    @Size(max = 20)
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 100)
    private String model;

    @NotNull(message = "Price is required")
    private Price price;

    @NotNull(message = "Body type is required")
    private BodyType bodyType;

    @NotNull(message = "Engine type is required")
    private EngineType engineType;

    @NotNull(message = "Power type is required")
    private CarPower power;

    @NotNull(message = "Engine volume is required")
    private EngineVolume engineVolume;
    @NotNull(message = "Transmission type is required")
    private TransmissionType transmissionType;

    @NotNull(message = "Driveshaft type is required")
    private DriveshaftType driveshaftType;

    @NotBlank(message = "Color is required")
    @Size(max = 20)
    private String color;

    @NotNull(message = "Test drive flag is required")
    private Boolean testDriveAllowed;

    @Builder.Default
    @NotNull(message = "Default spare parts are required")
    private Set<UUID> defaultSpareParts = new HashSet<>();

    @Builder.Default
    @NotNull(message = "Allowed spare parts are required")
    private Set<UUID> allowedSpareParts = new HashSet<>();
}
