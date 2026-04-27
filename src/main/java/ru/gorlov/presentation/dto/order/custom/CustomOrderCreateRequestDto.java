package ru.gorlov.presentation.dto.order.custom;

import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomOrderCreateRequestDto {

    @NotNull(message = "Client id is required")
    private UUID clientId;

    @NotNull(message = "Store manager id is required")
    private UUID storeManagerId;

    @NotNull(message = "Car id is required")
    private UUID carId;

    @Builder.Default
    @NotNull(message = "Configuration is required")
    private Set<UUID> configuration = new HashSet<>();
}
