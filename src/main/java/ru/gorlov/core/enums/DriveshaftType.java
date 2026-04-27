package ru.gorlov.core.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DriveshaftType {
    FRONT((short) 1),
    BACK((short) 2),
    FULL((short) 3);

    private static final Map<Short, DriveshaftType> BY_ID;

    static {
        BY_ID = Arrays.stream(values())
            .collect(Collectors.toMap(DriveshaftType::getId, r -> r));
    }

    private final Short id;

    public static DriveshaftType fromId(int id) {
        return BY_ID.get((short) id);
    }
}
