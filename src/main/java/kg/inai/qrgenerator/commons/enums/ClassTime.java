package kg.inai.qrgenerator.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClassTime {

    FIRST(8, 0),
    SECOND(9, 30),
    THIRD(11, 0),
    FOURTH(12, 30),
    FIFTH(14, 0),
    SIXTH(15, 30),
    SEVENTH(17, 0),
    EIGHTH(18, 30),
    NINTH(20, 0);

    private final int hours;

    private final int minutes;
}
