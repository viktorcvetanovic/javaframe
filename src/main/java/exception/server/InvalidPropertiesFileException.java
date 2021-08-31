package exception.server;

public class InvalidPropertiesFileException extends RuntimeException {
    public InvalidPropertiesFileException(String message) {
        super(message);
    }
}
