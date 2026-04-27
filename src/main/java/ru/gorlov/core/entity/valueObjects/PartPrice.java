package ru.gorlov.core.entity.valueObjects;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class PartPrice {

    @Column(name = "price", nullable = false, precision = 7, scale = 3)
    @JsonValue
    private BigDecimal value;

    public PartPrice(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Part price must be not null");
        }

        this.value = value;
    }

    public PartPrice add(PartPrice partPrice) {
        return new PartPrice(this.value.add(partPrice.getValue()));
    }

    public PartPrice sub(PartPrice partPrice) {
        return new PartPrice(this.value.add(partPrice.getValue().negate()));
    }
}
