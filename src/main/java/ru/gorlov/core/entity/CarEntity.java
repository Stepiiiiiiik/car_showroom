package ru.gorlov.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLRestriction;
import ru.gorlov.core.entity.valueObjects.CarPower;
import ru.gorlov.core.entity.valueObjects.EngineVolume;
import ru.gorlov.core.entity.valueObjects.Price;
import ru.gorlov.core.enums.BodyType;
import ru.gorlov.core.enums.DriveshaftType;
import ru.gorlov.core.enums.EngineType;
import ru.gorlov.core.enums.TransmissionType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cars")
public class CarEntity extends BaseEntity {

    @Column(name = "brand", nullable = false, length = 20)
    @FieldNameConstants.Include
    private String brand;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Embedded
    private Price price;

    @Column(name = "body_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private BodyType bodyType;

    @Column(name = "engine_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EngineType engineType;

    @Embedded
    private CarPower power;

    @Embedded
    private EngineVolume engineVolume;

    @Column(name = "transmission_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TransmissionType transmissionType;

    @Column(name = "driveshaft_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DriveshaftType driveshaftType;

    @Column(name = "color", nullable = false, length = 20)
    private String color;

    @Column(name = "test_drive_allowed", nullable = false)
    private Boolean testDriveAllowed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "car_default_spare_parts",
        joinColumns = @JoinColumn(name = "car_id"),
        inverseJoinColumns = @JoinColumn(name = "spare_part_id")
    )
    @SQLRestriction("removed = false")
    @BatchSize(size = 30)
    @Builder.Default
    private Set<SparePartEntity> defaultSpareParts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "cars_allowed_spare_parts",
        joinColumns = @JoinColumn(name = "car_id"),
        inverseJoinColumns = @JoinColumn(name = "spare_part_id")
    )
    @FieldNameConstants.Include
    @SQLRestriction("removed = false")
    @BatchSize(size = 30)
    @Builder.Default
    private Set<SparePartEntity> allowedSpareParts = new HashSet<>();
}
