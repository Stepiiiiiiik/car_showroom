package ru.gorlov.core.filters;

import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CarFilter implements Serializable {

    @Size(max = 20)
    private String brand;

    private Set<UUID> spareParts;
}
