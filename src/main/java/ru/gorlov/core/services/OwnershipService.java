package ru.gorlov.core.services;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gorlov.core.exceptions.EntityNotFoundException;
import ru.gorlov.core.utils.SecurityUtils;
import ru.gorlov.data.postgres.CommonOrderRepository;
import ru.gorlov.data.postgres.CustomOrderRepository;
import ru.gorlov.data.postgres.TestDriveRepository;

@RequiredArgsConstructor
@Service
public class OwnershipService {

    private final CommonOrderRepository commonOrderRepository;

    private final CustomOrderRepository customOrderRepository;

    private final TestDriveRepository testDriveRepository;

    private final SecurityUtils securityUtils;

    public boolean isCommonOrderOwner(UUID orderId) {
        return commonOrderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order with id=" + orderId + " was not found"))
            .getClient().getId().equals(UUID.fromString(securityUtils.getCurrentUserId()));
    }

    public boolean isCustomOrderOwner(UUID orderId) {
        return customOrderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order with id=" + orderId + " was not found"))
            .getClient().getId().equals(UUID.fromString(securityUtils.getCurrentUserId()));
    }

    public boolean isTestDriveOwner(UUID testDriveId) {
        return testDriveRepository.findById(testDriveId).orElseThrow(
                () -> new EntityNotFoundException(
                    "Test drive with id=" + testDriveId + " was not found"))
            .getClient().getId().equals(UUID.fromString(securityUtils.getCurrentUserId()));
    }
}
