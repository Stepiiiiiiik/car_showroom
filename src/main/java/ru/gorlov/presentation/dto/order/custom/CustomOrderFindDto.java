package ru.gorlov.presentation.dto.order.custom;

import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gorlov.core.entity.valueObjects.Price;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomOrderFindDto {

    @NotNull(message = "Order id is required")
    private UUID id;

    @NotNull(message = "Client id is required")
    private UUID clientId;

    @NotNull(message = "Store manager id is required")
    private UUID storeManagerId;

    @NotNull(message = "Car id is required")
    private UUID carId;

    @NotNull(message = "Price is required")
    private Price price;

    @NotNull(message = "State id is required")
    private Short stateId;

    @Builder.Default
    @NotNull(message = "Configuration is required")
    private Set<UUID> configuration = new HashSet<>();
}


