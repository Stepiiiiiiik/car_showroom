package ru.gorlov.presentation.controllers;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gorlov.core.entity.SparePartEntity;
import ru.gorlov.core.services.SparePartService;
import ru.gorlov.presentation.dto.part.SparePartCreateRequestDto;
import ru.gorlov.presentation.dto.part.SparePartCreateResponseDto;
import ru.gorlov.presentation.dto.part.SparePartFindDto;
import ru.gorlov.presentation.dto.part.SparePartUpdateDto;
import ru.gorlov.presentation.mapper.SparePartMapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sparepart")
public class SpatePartController {

    private final SparePartService sparePartService;

    private final SparePartMapper sparePartMapper;

    @PostMapping
    @PreAuthorize("hasRole('admin') or hasRole('warehouse_manager')")
    public ResponseEntity<SparePartCreateResponseDto> create(@Valid @RequestBody
    SparePartCreateRequestDto requestDto) {
        SparePartEntity sparePartEntity = sparePartMapper.toCreateEntity(requestDto);
        UUID sparePartId = sparePartService.createSparePart(sparePartEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new SparePartCreateResponseDto(sparePartId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('warehouse_manager')")
    public ResponseEntity<SparePartFindDto> get(@PathVariable UUID id) {
        SparePartFindDto dto = sparePartMapper.toFindDto(sparePartService.findSparePartById(id));

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('admin') or hasRole('warehouse_manager')")
    public ResponseEntity<List<SparePartFindDto>> getAll() {
        return ResponseEntity.ok(
            sparePartService.findAll().stream().map(sparePartMapper::toFindDto).toList());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('warehouse_manager')")
    public ResponseEntity<SparePartUpdateDto> update(@PathVariable UUID id,
        @Valid @RequestBody SparePartUpdateDto requestDto) {
        return ResponseEntity.ok(
            sparePartMapper.toUpdateDto(sparePartService.updateSparePart(id, requestDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sparePartService.deleteSparePart(id);

        return ResponseEntity.noContent().build();
    }
}
