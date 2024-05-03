package kg.inai.qrgenerator.commons.constants;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@Getter
@UtilityClass
public class Constants {

    public static final String STUDENT_HAS_NO_GROUP = "У студента нет группы";
    public static final String NOT_STUDENT = "Не студент";
    public static final Integer EARTH_RADIUS = 6371000;
    public static final Double LATITUDE = 42.874703;
    public static final Double LONGITUDE = 74.578668;
    public static final String[] PERMIT_URL =
            {"api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};
}
