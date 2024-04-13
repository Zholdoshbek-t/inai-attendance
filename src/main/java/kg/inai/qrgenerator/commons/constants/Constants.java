package kg.inai.qrgenerator.commons.constants;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@Getter
@UtilityClass
public class Constants {

    public static final String DESCR = "descr";
    public static final String CODE = "code";
    public static final String[] PERMIT_URL =
            {"api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};
}
