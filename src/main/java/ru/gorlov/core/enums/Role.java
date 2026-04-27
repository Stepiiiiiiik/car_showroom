package ru.gorlov.core.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    SHOWROOM_MANAGER((short) 1),
    WAREHOUS_ADMIN((short) 2),
    CLIENT((short) 3),
    SYSTEM_ADMIN((short) 4);

    private static final Map<Short, Role> BY_ID;

    static {
        BY_ID = Arrays.stream(values())
            .collect(Collectors.toMap(Role::getId, r -> r));
    }

    private final Short id;

    public static Role fromId(int id) {
        return BY_ID.get((short) id);
    }
}
