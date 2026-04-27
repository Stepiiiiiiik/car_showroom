package ru.gorlov.core.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransmissionType {
    MECHANIC((short) 1),
    AUTOMATIC((short) 2);

    private static final Map<Short, TransmissionType> BY_ID;

    static {
        BY_ID = Arrays.stream(values())
            .collect(Collectors.toMap(TransmissionType::getId, r -> r));
    }

    private final Short id;

    public static TransmissionType fromId(int id) {
        return BY_ID.get((short) id);
    }
}
