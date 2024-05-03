package kg.inai.qrgenerator.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "Администратор"),
    TEACHER("ROLE_TEACHER", "Учитель"),
    STUDENT("ROLE_STUDENT", "Студент");

    private final String name;
    private final String nameLocal;
}
