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
import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.services.CustomOrderService;
import ru.gorlov.core.services.OwnershipService;
import ru.gorlov.core.utils.SecurityUtils;
import ru.gorlov.presentation.dto.order.custom.CustomOrderCreateRequestDto;
import ru.gorlov.presentation.dto.order.custom.CustomOrderCreateResponseDto;
import ru.gorlov.presentation.dto.order.custom.CustomOrderFindDto;
import ru.gorlov.presentation.dto.order.custom.CustomOrderTransitRequestDto;
import ru.gorlov.presentation.mapper.CustomOrderMapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("orders/custom")
public class CustomOrderController {

    private final CustomOrderService customOrderService;

    private final OwnershipService ownershipService;

    private final CustomOrderMapper customOrderMapper;

    private final SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public ResponseEntity<List<CustomOrderFindDto>> getAll() {
        return ResponseEntity.ok(
            customOrderService.findAllCustomOrders().stream().map(customOrderMapper::toFindDto)
                .toList());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<List<CustomOrderFindDto>> getAllUserOrders() {
        String currentUserId = securityUtils.getCurrentUserId();

        Specification<CustomOrderEntity> spec = (root, query, cb) ->
            cb.equal(root.get("client").get("id"), UUID.fromString(currentUserId));

        return ResponseEntity.ok(
            customOrderService.findAllBySpec(spec).stream().map(customOrderMapper::toFindDto)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize(
        "(hasRole('admin') or "
            + "hasRole('manager') or "
            + "(hasRole('user') and @ownershipService.isCustomOrderOwner(#id)))")
    public ResponseEntity<CustomOrderFindDto> get(@PathVariable UUID id) {
        CustomOrderFindDto dto = customOrderMapper.toFindDto(
            customOrderService.findCustomOrderById(id));

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('user') and @securityUtils.hasRole(#requestDto.storeManagerId, 'manager')")
    public ResponseEntity<CustomOrderCreateResponseDto> create(
        @Valid @RequestBody CustomOrderCreateRequestDto requestDto) {
        CustomOrderEntity customOrderEntity = customOrderMapper.toCreateEntity(requestDto);
        UUID customOrderId = customOrderService.createCustomOrder(customOrderEntity,
            requestDto.getCarId(), UUID.fromString(securityUtils.getCurrentUserId()),
            requestDto.getStoreManagerId(),
            requestDto.getConfiguration());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CustomOrderCreateResponseDto(customOrderId));
    }

    @PatchMapping("/transit/{id}")
    @PreAuthorize(
        "(hasRole('admin') or "
            + "(hasRole('user') and #requestDto.state == T(ru.gorlov.core.enums.CustomOrderState).CANCELED and @ownershipService.isCustomOrderOwner(#id)) "
            + "or (hasRole('warehouse_manager') and #requestDto.state == T(ru.gorlov.core.enums.CustomOrderState).AGREED))")
    public ResponseEntity<Void> transit(@PathVariable UUID id, @Valid @RequestBody
    CustomOrderTransitRequestDto requestDto) {
        customOrderService.transitTo(id, requestDto.getState());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        customOrderService.deleteCustomOrder(id);

        return ResponseEntity.noContent().build();
    }
}
