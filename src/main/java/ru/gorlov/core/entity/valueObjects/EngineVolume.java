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
public class EngineVolume {

    @Column(name = "engine_volume", nullable = false, precision = 2, scale = 1)
    @JsonValue
    private BigDecimal value;

    public EngineVolume(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Engine volume must be greater then 0 and not null");
        }

        this.value = value;
    }

    public int compareTo(EngineVolume externalVolume) {
        return value.compareTo(externalVolume.getValue());
    }
}
