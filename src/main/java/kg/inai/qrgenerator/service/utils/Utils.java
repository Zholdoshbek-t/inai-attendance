package kg.inai.qrgenerator.service.utils;

import kg.inai.qrgenerator.entity.User;
import lombok.experimental.UtilityClass;

import static kg.inai.qrgenerator.commons.constants.Constants.*;

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

    public static Boolean isInTheUniRange(double studentLatitude, double studentLongitude) {

        double latDiff = Math.toRadians(studentLatitude - LATITUDE);
        double lonDiff = Math.toRadians(studentLongitude - LONGITUDE);

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(LATITUDE)) * Math.cos(Math.toRadians(studentLatitude)) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS * c <= 100);
    }
}
