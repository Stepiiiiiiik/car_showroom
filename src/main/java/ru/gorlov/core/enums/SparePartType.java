package ru.gorlov.core.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SparePartType {
    WHEELS((short) 1),
    TRANSMISSION((short) 2),
    STEERING_WHEEL((short) 3),
    INTERIOR((short) 4);

    private static final Map<Short, SparePartType> BY_ID;

    static {
        BY_ID = Arrays.stream(values())
            .collect(Collectors.toMap(SparePartType::getId, r -> r));
    }

    private final Short id;

    public static SparePartType fromId(int id) {
        return BY_ID.get((short) id);
    }
}
