package ru.gorlov.presentation.controllers;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
import ru.gorlov.core.entity.CarEntity;
import ru.gorlov.core.filters.CarFilter;
import ru.gorlov.core.services.CarService;
import ru.gorlov.presentation.dto.car.CarCreateRequestDto;
import ru.gorlov.presentation.dto.car.CarCreateResponseDto;
import ru.gorlov.presentation.dto.car.CarFindDto;
import ru.gorlov.presentation.dto.car.CarUpdateDto;
import ru.gorlov.presentation.mapper.CarMapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    private final CarMapper carMapper;

    @PostMapping
    @PreAuthorize("hasRole('warehouse_manager') or hasRole('admin')")
    public ResponseEntity<CarCreateResponseDto> create(
        @Valid @RequestBody CarCreateRequestDto requestDto) {
        CarEntity carEntity = carMapper.toCreateEntity(requestDto);
        UUID carId = carService.createCar(carEntity, requestDto.getDefaultSpareParts(),
            requestDto.getAllowedSpareParts());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CarCreateResponseDto(carId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('user') or hasRole('manager') or hasRole('warehouse_manager') or hasRole('admin')")
    public ResponseEntity<CarFindDto> get(@PathVariable UUID id) {
        CarFindDto dto = carMapper.toFindDto(carService.findCarById(id));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('user') or hasRole('manager') or hasRole('warehouse_manager') or hasRole('admin')")
    public ResponseEntity<List<CarFindDto>> getFiltered(@Valid @ParameterObject CarFilter filter) {
        return ResponseEntity.ok(
            carService.findCarsByFilter(filter).stream().map(carMapper::toFindDto).toList());
    }

    @GetMapping
    @PreAuthorize("hasRole('user') or hasRole('manager') or hasRole('warehouse_manager') or hasRole('admin')")
    public ResponseEntity<List<CarFindDto>> getAllCars() {
        return ResponseEntity.ok(carService.findAll().stream().map(carMapper::toFindDto).toList());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('warehouse_manager') or hasRole('admin')")
    public ResponseEntity<CarUpdateDto> update(@PathVariable UUID id,
        @Valid @RequestBody CarUpdateDto requestDto) {
        return ResponseEntity.ok(
            carMapper.toUpdateDto(carService.updateCar(id, requestDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        carService.deleteCar(id);

        return ResponseEntity.noContent().build();
    }
}
