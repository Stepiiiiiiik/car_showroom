package ru.gorlov.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gorlov.core.entity.valueObjects.PartPrice;
import ru.gorlov.core.enums.SparePartType;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "spare_parts")
public class SparePartEntity extends BaseEntity {

    @Embedded
    private PartPrice price;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SparePartType type;
}
