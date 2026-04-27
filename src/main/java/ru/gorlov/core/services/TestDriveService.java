package ru.gorlov.core.services;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gorlov.core.entity.CarEntity;
import ru.gorlov.core.entity.RoleEntity;
import ru.gorlov.core.entity.TestDriveEntity;
import ru.gorlov.core.entity.UserEntity;
import ru.gorlov.core.exceptions.EntityNotFoundException;
import ru.gorlov.core.exceptions.IncompatibleRoleException;
import ru.gorlov.data.postgres.CarRepository;
import ru.gorlov.data.postgres.TestDriveRepository;
import ru.gorlov.data.postgres.UserRepository;
import ru.gorlov.presentation.dto.testdrive.TestDriveUpdateDto;
import ru.gorlov.presentation.mapper.TestDriveMapper;

@RequiredArgsConstructor
@Service
public class TestDriveService {

    private final TestDriveMapper testDriveMapper;

    private final TestDriveRepository testDriveRepository;

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    @Transactional
    public UUID createTestDrive(
        TestDriveEntity testDriveEntity, UUID clientId, UUID carId) {
        CarEntity carEntity = carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException(
                "Car with id=" + carId + " was not found"));

        if (!carEntity.getTestDriveAllowed()) {
            throw new IllegalArgumentException(
                "Car with id=" + carId + " is not allowed for test drive");
        }

        UserEntity clientEntity = userRepository.findById(clientId)
            .orElseThrow(() -> new EntityNotFoundException(
                "Client with id=" + clientId + " was not found"));

        testDriveEntity.setCar(carEntity);
        testDriveEntity.setClient(clientEntity);

        testDriveRepository.save(testDriveEntity);

        return testDriveEntity.getId();
    }

    @Transactional(readOnly = true)
    public TestDriveEntity findTestDriveById(UUID id) {
        return testDriveRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Test drive with id=" + id + " was not found"));
    }

    @Transactional
    public TestDriveEntity updateTestDrive(UUID id, TestDriveUpdateDto testDriveUpdateDto) {
        TestDriveEntity testDriveEntity = testDriveRepository.findById(id)
            .orElseThrow(() ->
                new EntityNotFoundException("Test drive with id=" + id
                    + " was not found"));

        testDriveMapper.toUpdateEntity(testDriveEntity, testDriveUpdateDto);

        if (testDriveUpdateDto.getCarId() != null) {
            CarEntity carEntity = carRepository.findById(testDriveUpdateDto.getCarId())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Car with id=" + testDriveUpdateDto.getCarId() + " was not found"));

            if (!carEntity.getTestDriveAllowed()) {
                throw new IllegalArgumentException(
                    "Car with id=" + testDriveUpdateDto.getCarId()
                        + " is not allowed for test drive");
            }

            testDriveEntity.setCar(carEntity);
        }

        testDriveRepository.save(testDriveEntity);

        return testDriveEntity;
    }

    @Transactional
    public void deleteTestDrive(UUID id) {
        if (!testDriveRepository.existsById(id)) {
            throw new EntityNotFoundException("Test drive with id=" + id + " was not found");
        }
        testDriveRepository.deleteById(id);
    }
}
