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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import ru.gorlov.core.entity.valueObjects.Price;
import ru.gorlov.core.enums.CustomOrderState;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "custom_orders")
public class CustomOrderEntity extends BaseEntity {

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "custom_order_parts", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "spare_part_id"))
    @BatchSize(size = 30)
    private Set<SparePartEntity> configuration = new HashSet<>();

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CustomOrderState state;
}
