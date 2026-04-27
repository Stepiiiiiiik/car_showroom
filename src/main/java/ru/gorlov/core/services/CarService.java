package ru.gorlov.core.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gorlov.core.entity.CarEntity;
import ru.gorlov.core.entity.SparePartEntity;
import ru.gorlov.core.enums.SparePartType;
import ru.gorlov.core.exceptions.EntityNotFoundException;
import ru.gorlov.core.exceptions.IncompatibleComponentException;
import ru.gorlov.core.filters.CarFilter;
import ru.gorlov.core.specifications.CarEntitySpecification;
import ru.gorlov.data.postgres.CarRepository;
import ru.gorlov.data.postgres.SparePartRepository;
import ru.gorlov.presentation.dto.car.CarUpdateDto;
import ru.gorlov.presentation.mapper.CarMapper;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarMapper carMapper;

    private final CarRepository carRepository;

    private final SparePartRepository sparePartRepository;

    @Transactional(readOnly = true)
    public CarEntity findCarById(UUID id) {
        return carRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }

    @Transactional(readOnly = true)
    public List<CarEntity> findAll() {
        return carRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CarEntity> findCarsByFilter(CarFilter filter) {
        CarEntitySpecification spec = new CarEntitySpecification(filter);

        return carRepository.findAll(spec);
    }

    @Transactional
    public UUID createCar(CarEntity carEntity, Set<UUID> defaultSpareParts,
        Set<UUID> allowedSpareParts) {

        carEntity.setDefaultSpareParts(idsToSet(defaultSpareParts));

        carEntity.setAllowedSpareParts(idsToSet(allowedSpareParts));

        validateCarSpareParts(carEntity);

        carRepository.save(carEntity);

        return carEntity.getId();
    }

    @Transactional
    public CarEntity updateCar(UUID id, CarUpdateDto requestDto) {
        CarEntity carEntity = carRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Car part with id=" + id
                    + " was not found"));

        carMapper.toUpdatedEntity(carEntity, requestDto);

        if (requestDto.getDefaultSpareParts() != null) {
            carEntity.setDefaultSpareParts(idsToSet(requestDto.getDefaultSpareParts()));
        }

        if (requestDto.getAllowedSpareParts() != null) {
            carEntity.setAllowedSpareParts(idsToSet(requestDto.getAllowedSpareParts()));
        }

        validateCarSpareParts(carEntity);

        carRepository.save(carEntity);

        return carEntity;
    }

    @Transactional
    public void deleteCar(UUID id) {
        if (!carRepository.existsById(id)) {
            throw new EntityNotFoundException("Car with id=" + id + " was not found");
        }
        carRepository.deleteById(id);
    }

    private Set<SparePartEntity> idsToSet(Set<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }

        List<SparePartEntity> found = sparePartRepository.findAllById(ids);

        if (found.size() != ids.size()) {
            throw new EntityNotFoundException("Some default spare parts not found");
        }

        return new HashSet<>(found);
    }

    private boolean hasUniqueTypes(Set<SparePartEntity> spareParts) {
        Set<SparePartType> types = new HashSet<>();
        for (SparePartEntity sparePart : spareParts) {
            if (!types.add(sparePart.getType())) {
                return false;
            }
        }

        return true;
    }

    private boolean isDefaultsInAllowed(Set<SparePartEntity> defaults,
        Set<SparePartEntity> allowed) {
        for (SparePartEntity defaultPart : defaults) {
            if (!allowed.contains(defaultPart)) {
                return false;
            }
        }

        return true;
    }

    private void validateCarSpareParts(CarEntity carEntity) {
        if (!isDefaultsInAllowed(carEntity.getDefaultSpareParts(),
            carEntity.getAllowedSpareParts())) {
            throw new IncompatibleComponentException(
                "Default spare part is not in the allowed spare parts list for this car");
        }
        if (!hasUniqueTypes(carEntity.getDefaultSpareParts())) {
            throw new IllegalArgumentException("Duplicate spare part type in defaults");
        }
    }
}
