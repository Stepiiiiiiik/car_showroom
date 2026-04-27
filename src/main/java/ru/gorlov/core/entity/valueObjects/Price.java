package ru.gorlov.core.entity.valueObjects;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Price {

    @Column(name = "price", nullable = false, precision = 7, scale = 3)
    @JsonValue
    private BigDecimal value;


    public Price(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be greater then 0 and not null");
        }

        this.value = value;
    }

    public int compareTo(Price externalPrice) {
        if (externalPrice == null) {
            throw new IllegalArgumentException("External price cannot be null");
        }

        return this.value.compareTo(externalPrice.value);
    }

    public Price add(PartPrice partPrice) {
        return new Price(this.value.add(partPrice.getValue()).max(BigDecimal.ZERO));
    }
}
