package ru.gorlov.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gorlov.core.entity.valueObjects.Price;
import ru.gorlov.core.enums.CommonOrderState;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "common_orders")
public class CommonOrderEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserEntity client;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private UserEntity storeManager;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarEntity car;

    @Embedded
    private Price price;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CommonOrderState state;
}
