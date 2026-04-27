package ru.gorlov.core.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonOrderState {
    AGREED((short) 1),
    AWAITING_PAYMENT((short) 2),
    CANCELED((short) 3),
    COMPLETED((short) 4),
    PAID((short) 5),
    PLACED((short) 6),
    READY_FOR_PICKUP((short) 7);

    private static final Map<Short, CommonOrderState> BY_ID;

    static {
        BY_ID = Arrays.stream(values())
            .collect(Collectors.toMap(CommonOrderState::getId, r -> r));
    }

    private final Short id;

    public static CommonOrderState fromId(int id) {
        return BY_ID.get((short) id);
    }
}
