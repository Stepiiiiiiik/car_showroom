package ru.gorlov.core.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gorlov.core.entity.CarEntity;
import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.entity.RoleEntity;
import ru.gorlov.core.entity.SparePartEntity;
import ru.gorlov.core.entity.UserEntity;
import ru.gorlov.core.entity.states.custom.CustomOrderStateAbstraction;
import ru.gorlov.core.entity.states.custom.mapper.CustomOrderStateMapper;
import ru.gorlov.core.entity.valueObjects.Price;
import ru.gorlov.core.enums.CustomOrderState;
import ru.gorlov.core.enums.SparePartType;
import ru.gorlov.core.exceptions.EntityNotFoundException;
import ru.gorlov.core.exceptions.IncompatibleComponentException;
import ru.gorlov.core.exceptions.IncompatibleRoleException;
import ru.gorlov.data.postgres.CarRepository;
import ru.gorlov.data.postgres.CustomOrderRepository;
import ru.gorlov.data.postgres.SparePartRepository;
import ru.gorlov.data.postgres.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomOrderService {

    private final CustomOrderStateMapper customOrderStateMapper;

    private final CustomOrderRepository customOrderRepository;

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final SparePartRepository sparePartRepository;

    @Transactional
    public UUID createCustomOrder(
        CustomOrderEntity customOrderEntity, UUID carId, UUID clientId, UUID storeManagerId,
        Set<UUID> configurationIds) {
        CarEntity carEntity = carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException(
                "Car with id=" + carId + " was not found"));

        UserEntity clientEntity = userRepository.findById(clientId)
            .orElseThrow(() -> new EntityNotFoundException(
                "Client with id=" + clientId + " was not found"));

        UserEntity storeManagerEntity = userRepository.findById(
            storeManagerId).orElseThrow(
            () -> new EntityNotFoundException(
                "Store manager with id=" + storeManagerId
                    + " was not found"));

        Set<SparePartEntity> inputConfiguration = idsToSet(
            configurationIds);

        if (!hasUniqueTypes(inputConfiguration)) {
            throw new IllegalArgumentException("Spare part types must be unique");
        }

        Map<SparePartType, SparePartEntity> configuration = carEntity.getDefaultSpareParts()
            .stream().collect(Collectors.toMap(SparePartEntity::getType, r -> r));

        Map<SparePartType, SparePartEntity> defaultConfiguration = carEntity.getDefaultSpareParts()
            .stream().collect(Collectors.toMap(SparePartEntity::getType, r -> r));

        for (SparePartEntity sparePartEntity : inputConfiguration) {
            if (!configuration.containsKey(sparePartEntity.getType())) {
                throw new IncompatibleComponentException("Not allowed spare part type");
            }

            configuration.put(sparePartEntity.getType(), sparePartEntity);
        }

        Price price = carEntity.getPrice();
        for (SparePartType sparePartType : configuration.keySet()) {
            price = price.add(configuration.get(sparePartType).getPrice()
                .sub(defaultConfiguration.get(sparePartType).getPrice()));
        }

        customOrderEntity.setState(CustomOrderState.PLACED);
        customOrderEntity.setCar(carEntity);
        customOrderEntity.setPrice(price);
        customOrderEntity.setClient(clientEntity);
        customOrderEntity.setStoreManager(storeManagerEntity);
        customOrderEntity.setConfiguration(new HashSet<>(configuration.values()));

        customOrderRepository.save(customOrderEntity);

        return customOrderEntity.getId();
    }

    @Transactional(readOnly = true)
    public List<CustomOrderEntity> findAllCustomOrders() {
        return customOrderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomOrderEntity findCustomOrderById(UUID id) {
        return customOrderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Custom order with id=" + id + " was not found"));
    }

    @Transactional(readOnly = true)
    public List<CustomOrderEntity> findAllBySpec(Specification<CustomOrderEntity> spec) {
        return customOrderRepository.findAll(spec);
    }

    @Transactional
    public void deleteCustomOrder(UUID id) {
        if (!customOrderRepository.existsById(id)) {
            throw new EntityNotFoundException("Common order with id=" + id + " was not found");
        }

        customOrderRepository.deleteById(id);
    }

    @Transactional
    public void transitTo(UUID id, CustomOrderState stateEnum) {
        CustomOrderEntity customOrderEntity = customOrderRepository.findById(
                id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Custom order with id=" + id + " was not found"));

        CustomOrderStateAbstraction state = customOrderStateMapper.map(customOrderEntity);

        state.transitTo(customOrderEntity, stateEnum);

        customOrderRepository.save(customOrderEntity);
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
}
