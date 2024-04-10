package kg.inai.qrgenerator.commons.constants;


public final class Endpoints {

    private static final String API = "/api";
    private static final String V1 = "/v1";
    private static final String QR_V1 = API + V1;
    public static final String GROUP_URL = QR_V1 + "/group";
    public static final String ATTENDANCE_URL = QR_V1 + "/attendance";
    public static final String AUTH_URL = QR_V1 + "/auth";
    public static final String QR_URL = QR_V1 + "/qr";
    public static final String STATISTICS_URL = QR_V1 + "/statistics";
    public static final String SUBJECT_URL = QR_V1 + "/subject";
    public static final String USER_URL = QR_V1 + "/user";

    private Endpoints() {
    }
}
