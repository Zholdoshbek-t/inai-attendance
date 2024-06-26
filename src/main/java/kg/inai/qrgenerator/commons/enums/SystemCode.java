package kg.inai.qrgenerator.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SystemCode {

    SUCCESS(200, "Успешно"),
    USER_NOT_FOUND(1, "Пользователь не найден"),
    GROUP_NOT_FOUND(1, "Группа не найдена"),
    SUBJECT_NOT_FOUND(1, "Предмет не найден"),
    SUBJECT_SCHEDULE_NOT_FOUND(1, "Пара предмета не найден"),
    VALUE_NOT_FOUND(1, "Объект не найден"),
    STUDENT_NOT_FOUND_IN_GROUP(1, "Группа не включает переданного студента"),
    ALREADY_EXISTS(2, "Уже существует"),
    TEACHER_HAS_SUBJECT(2, "Учитель уже имеет существующую пару в данное время и день"),
    JWT_EXCEPTION(3, "JWT не актуален или не начинается с Bearer"),
    TOKEN_IS_EXPIRED(4, "Время JWT просрочено"),
    LECTURE_TIME_EXPIRE(4, "Время актуальности QR-Кода просрочено"),
    NOT_IN_THE_RANGE(4, "Вы находитесь не в зоне университета"),
    INCORRECT_PASSWORD(4, "Неправильный пароль"),
    INTERNAL_SERVER_ERROR(999, "Внутренняя ошибка сервера");

    private final Integer code;

    private final String message;
}
