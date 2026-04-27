package ru.gorlov.core.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomOrderState {
    AGREED((short) 1),
    AWAITING_DELIVERY((short) 2),
    AWAITING_PAYMENT((short) 3),
    CANCELED((short) 4),
    COMPLETED((short) 5),
    PAID((short) 6),
    PLACED((short) 7),
    READY_FOR_PICKUP((short) 8);

    private static final Map<Short, CustomOrderState> BY_ID;

    static {
        BY_ID = Arrays.stream(values())
            .collect(Collectors.toMap(CustomOrderState::getId, r -> r));
    }

    private final Short id;

    public static CustomOrderState fromId(int id) {
        return BY_ID.get((short) id);
    }
}
