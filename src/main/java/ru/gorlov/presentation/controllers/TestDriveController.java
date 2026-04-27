package ru.gorlov.presentation.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import ru.gorlov.core.entity.TestDriveEntity;
import ru.gorlov.core.services.TestDriveService;
import ru.gorlov.core.utils.SecurityUtils;
import ru.gorlov.presentation.dto.testdrive.TestDriveCreateRequestDto;
import ru.gorlov.presentation.dto.testdrive.TestDriveCreateResponseDto;
import ru.gorlov.presentation.dto.testdrive.TestDriveFindDto;
import ru.gorlov.presentation.dto.testdrive.TestDriveUpdateDto;
import ru.gorlov.presentation.mapper.TestDriveMapper;

@RequiredArgsConstructor
@RequestMapping("/testdrives")
@RestController
public class TestDriveController {

    private final TestDriveService testDriveService;

    private final TestDriveMapper testDriveMapper;
    private final SecurityUtils securityUtils;

    @PostMapping
    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public ResponseEntity<TestDriveCreateResponseDto> create(
        @Valid @RequestBody TestDriveCreateRequestDto requestDto) {
        TestDriveEntity testDriveEntity = testDriveMapper.toCreateEntity(requestDto);
        UUID testDriveId = testDriveService.createTestDrive(testDriveEntity,
            UUID.fromString(securityUtils.getCurrentUserId()), requestDto.getCarId());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new TestDriveCreateResponseDto(testDriveId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public ResponseEntity<TestDriveFindDto> get(@NotNull @PathVariable UUID id) {
        TestDriveFindDto dto = testDriveMapper.toFindDto(testDriveService.findTestDriveById(id));

        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('admin') or (hasRole('user') and @ownershipService.isTestDriveOwner(#id))")
    public ResponseEntity<TestDriveUpdateDto> update(@NotNull @PathVariable UUID id,
        @Valid @RequestBody TestDriveUpdateDto requestDto) {
        return ResponseEntity.ok(
            testDriveMapper.toUpdateDto(testDriveService.updateTestDrive(id, requestDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        testDriveService.deleteTestDrive(id);

        return ResponseEntity.noContent().build();
    }
}
