package kg.inai.qrgenerator.service.utils;

import kg.inai.qrgenerator.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    public static String getFullName(User student) {

        var strBuilder = new StringBuilder();

        strBuilder.append(student.getLastName());
        strBuilder.append(" ");
        strBuilder.append(student.getFirstName());

        if (!student.getMiddleName().isEmpty()) {
            strBuilder.append(" ");
            strBuilder.append(student.getMiddleName());
        }

        return strBuilder.toString();
    }
}
