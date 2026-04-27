package ru.gorlov.presentation.controllers;


import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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
import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.services.CommonOrderService;
import ru.gorlov.core.services.OwnershipService;
import ru.gorlov.core.utils.SecurityUtils;
import ru.gorlov.presentation.dto.order.common.CommonOrderCreateRequestDto;
import ru.gorlov.presentation.dto.order.common.CommonOrderCreateResponseDto;
import ru.gorlov.presentation.dto.order.common.CommonOrderFindDto;
import ru.gorlov.presentation.dto.order.common.CommonOrderTransitRequestDto;
import ru.gorlov.presentation.mapper.CommonOrderMapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders/common")
public class CommonOrderController {

    private final CommonOrderService commonOrderService;

    private final OwnershipService ownershipService;

    private final CommonOrderMapper commonOrderMapper;

    private final SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public ResponseEntity<List<CommonOrderFindDto>> getAll() {
        return ResponseEntity.ok(
            commonOrderService.findAllOrders().stream().map(commonOrderMapper::toFindDto)
                .toList());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<List<CommonOrderFindDto>> getAllUserOrders() {
        String currentUserId = securityUtils.getCurrentUserId();

        Specification<CommonOrderEntity> spec = (root, query, cb) ->
            cb.equal(root.get("client").get("id"), UUID.fromString(currentUserId));

        return ResponseEntity.ok(
            commonOrderService.findAllBySpec(spec).stream().map(commonOrderMapper::toFindDto)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize(
        "(hasRole('admin') or "
            + "hasRole('manager') or "
            + "(hasRole('user') and @ownershipService.isCommonOrderOwner(#id)))")
    public ResponseEntity<CommonOrderFindDto> get(@PathVariable UUID id) {
        CommonOrderFindDto dto = commonOrderMapper.toFindDto(
            commonOrderService.findCommonOrderById(id));

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('user') and @securityUtils.hasRole(#requestDto.storeManagerId, 'manager')")
    public ResponseEntity<CommonOrderCreateResponseDto> create(
        @Valid @RequestBody CommonOrderCreateRequestDto requestDto) {
        CommonOrderEntity commonOrderEntity = commonOrderMapper.toCreateEntity(requestDto);
        UUID commonOrderId = commonOrderService.createCommonOrder(commonOrderEntity,
            requestDto.getCarId(),
            UUID.fromString(securityUtils.getCurrentUserId()), requestDto.getStoreManagerId());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CommonOrderCreateResponseDto(commonOrderId));
    }

    @PatchMapping("/transit/{id}")
    @PreAuthorize(
        "(hasRole('admin') or "
            + "(hasRole('user') and #requestDto.state == T(ru.gorlov.core.enums.CommonOrderState).CANCELED and @ownershipService.isCommonOrderOwner(#id)) "
            + "or (hasRole('manager') and #requestDto.state == T(ru.gorlov.core.enums.CommonOrderState).AGREED))")
    public ResponseEntity<Void> transit(@PathVariable UUID id, @Valid @RequestBody
    CommonOrderTransitRequestDto requestDto) {
        commonOrderService.transitTo(id, requestDto.getState());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        commonOrderService.deleteCommonOrder(id);

        return ResponseEntity.noContent().build();
    }
}
