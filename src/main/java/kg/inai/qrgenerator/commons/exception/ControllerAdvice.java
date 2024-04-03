package kg.inai.qrgenerator.commons.exception;

import kg.inai.qrgenerator.commons.enums.SystemCode;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private RestResponse createRestResponse(SystemCode systemCode) {
        return RestResponse.builder()
                .message(systemCode.getMessage())
                .code(systemCode.getCode())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    public RestResponse handleAlreadyExistsException(AlreadyExistsException exception) {
        return createRestResponse(exception.getSystemCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public RestResponse handleNotFoundException(NotFoundException exception) {
        return createRestResponse(exception.getSystemCode());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerErrorException.class)
    public RestResponse handleServerErrorException(ServerErrorException exception) {
        return createRestResponse(exception.getSystemCode());
    }
}
