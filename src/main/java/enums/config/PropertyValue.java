package enums.config;

public enum PropertyValue {
    SERVER_IP, SERVER_PORT, SERVER_BACKLOG;


    public static PropertyValue convertStringToEnum(String s) {
        switch (s) {
            case "server_ip":
                return SERVER_IP;
            case "server_port":
                return SERVER_PORT;
            case "server_backlog":
                return SERVER_BACKLOG;
            default:
                return null;
        }
    }
}
