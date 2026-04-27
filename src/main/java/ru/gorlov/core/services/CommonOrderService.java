package ru.gorlov.core.services;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gorlov.core.entity.CarEntity;
import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.entity.RoleEntity;
import ru.gorlov.core.entity.UserEntity;
import ru.gorlov.core.entity.states.common.CommonOrderStateAbstraction;
import ru.gorlov.core.entity.states.common.mapper.CommonOrderStateMapper;
import ru.gorlov.core.enums.CommonOrderState;
import ru.gorlov.core.exceptions.EntityNotFoundException;
import ru.gorlov.core.exceptions.IncompatibleRoleException;
import ru.gorlov.data.postgres.CarRepository;
import ru.gorlov.data.postgres.CommonOrderRepository;
import ru.gorlov.data.postgres.UserRepository;

@RequiredArgsConstructor
@Service
public class CommonOrderService {

    private final CommonOrderStateMapper commonOrderStateMapper;

    private final CommonOrderRepository commonOrderRepository;

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    @Transactional
    public UUID createCommonOrder(
        CommonOrderEntity commonOrderEntity, UUID carId, UUID clientId, UUID storeManagerId) {
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

        commonOrderEntity.setState(CommonOrderState.PLACED);
        commonOrderEntity.setCar(carEntity);
        commonOrderEntity.setPrice(carEntity.getPrice());
        commonOrderEntity.setClient(clientEntity);
        commonOrderEntity.setStoreManager(storeManagerEntity);

        commonOrderRepository.save(commonOrderEntity);

        return commonOrderEntity.getId();
    }

    @Transactional(readOnly = true)
    public List<CommonOrderEntity> findAllOrders() {
        return commonOrderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CommonOrderEntity> findAllBySpec(Specification<CommonOrderEntity> spec) {
        return commonOrderRepository.findAll(spec);
    }

    @Transactional(readOnly = true)
    public CommonOrderEntity findCommonOrderById(UUID id) {
        return commonOrderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Common order with id=" + id + " was not found"));
    }

    @Transactional
    public void deleteCommonOrder(UUID id) {
        if (!commonOrderRepository.existsById(id)) {
            throw new EntityNotFoundException("Common order with id=" + id + " was not found");
        }
        commonOrderRepository.deleteById(id);
    }

    @Transactional
    public void transitTo(UUID id, CommonOrderState stateEnum) {
        CommonOrderEntity orderEntity = commonOrderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Common order with id=" + id + " was not found"));

        CommonOrderStateAbstraction state = commonOrderStateMapper.map(orderEntity);

        state.transitTo(orderEntity, stateEnum);

        commonOrderRepository.save(orderEntity);
    }
}
