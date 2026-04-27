package ru.gorlov.core.entity.valueObjects;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class CarPower {

    @Column(name = "power", nullable = false)
    @JsonValue
    private Integer value;

    public CarPower(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Power must be greater then 0 and not null");
        }

        this.value = value;
    }

    public int compareTo(CarPower externalPower) {
        return value.compareTo(externalPower.getValue());
    }
}
