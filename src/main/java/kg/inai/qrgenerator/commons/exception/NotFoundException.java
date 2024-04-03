package kg.inai.qrgenerator.commons.exception;

import kg.inai.qrgenerator.commons.enums.SystemCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    private SystemCode systemCode;
}
