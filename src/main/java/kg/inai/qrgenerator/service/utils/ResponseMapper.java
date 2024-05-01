package kg.inai.qrgenerator.service.utils;

import kg.inai.qrgenerator.controller.dto.RestResponse;
import lombok.experimental.UtilityClass;

import static kg.inai.qrgenerator.commons.enums.SystemCode.SUCCESS;

@UtilityClass
public class ResponseMapper {

    public static RestResponse responseSuccess() {
        return RestResponse.builder()
                .message(SUCCESS.getMessage())
                .code(SUCCESS.getCode())
                .build();
    }
}
