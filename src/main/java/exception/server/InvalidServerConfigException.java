package exception.server;

public class InvalidServerConfigException extends RuntimeException {
    public InvalidServerConfigException(String message) {
        super(message);
    }
}
