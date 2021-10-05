package enums.config;

public enum PropertyValue {
    SERVER_IP, SERVER_PORT, SERVER_BACKLOG, STATIC_CONTENT;


    public static PropertyValue convertStringToEnum(String s) {
        switch (s) {
            case "server_ip":
                return SERVER_IP;
            case "server_port":
                return SERVER_PORT;
            case "server_backlog":
                return SERVER_BACKLOG;
            case "static_contect":
                return STATIC_CONTENT;
            default:
                return null;
        }
    }
}
