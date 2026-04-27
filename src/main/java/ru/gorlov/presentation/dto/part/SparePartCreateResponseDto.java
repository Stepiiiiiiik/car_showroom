package ru.gorlov.presentation.dto.part;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparePartCreateResponseDto {

    @NotNull(message = "Id is required")
    private UUID id;
}
