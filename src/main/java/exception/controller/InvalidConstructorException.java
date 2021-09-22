package exception.controller;

public class InvalidConstructorException extends RuntimeException{
    public InvalidConstructorException(String message) {
        super(message);
    }
}
