package kg.inai.qrgenerator.controller.advice;

import kg.inai.qrgenerator.commons.enums.SystemCode;
import kg.inai.qrgenerator.commons.exception.AlreadyExistsException;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.commons.exception.ServerErrorException;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private RestResponse createRestResponse(SystemCode systemCode) {
        return RestResponse.builder()
                .message(systemCode.getMessage())
                .code(systemCode.getCode())
                .build();
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<RestResponse> handleAlreadyExistsException(AlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(createRestResponse(exception.getSystemCode()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.badRequest().body(createRestResponse(exception.getSystemCode()));
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<RestResponse> handleServerErrorException(ServerErrorException exception) {
        return ResponseEntity.badRequest().body(createRestResponse(exception.getSystemCode()));
    }
}
