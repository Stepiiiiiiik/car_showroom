package ru.gorlov.core.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BodyType {
    SEDAN((short) 1),
    UNIVERSAL((short) 2),
    COUPE((short) 3);

    private static final Map<Short, BodyType> BY_ID;

    static {
        BY_ID = Arrays.stream(values())
            .collect(Collectors.toMap(BodyType::getId, r -> r));
    }

    private final short id;

    public static BodyType fromId(int id) {
        return BY_ID.get((short) id);
    }
}
