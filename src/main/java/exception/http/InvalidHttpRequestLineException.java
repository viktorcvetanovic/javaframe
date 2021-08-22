package exception.http;

public class InvalidHttpRequestLineException extends RuntimeException {
    public InvalidHttpRequestLineException(String message) {
        super(message);
    }
}
