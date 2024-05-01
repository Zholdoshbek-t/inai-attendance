package kg.inai.qrgenerator.service.utils;

import kg.inai.qrgenerator.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    public static String getFullName(User user) {

        var strBuilder = new StringBuilder();

        strBuilder.append(user.getLastName());
        strBuilder.append(" ");
        strBuilder.append(user.getFirstName());

        if (!user.getMiddleName().isEmpty()) {
            strBuilder.append(" ");
            strBuilder.append(user.getMiddleName());
        }

        return strBuilder.toString();
    }
}
