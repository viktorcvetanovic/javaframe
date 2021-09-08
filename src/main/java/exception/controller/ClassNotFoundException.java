package exception.controller;

public class ClassNotFoundException extends RuntimeException{
    public ClassNotFoundException(String message) {
        super(message);
    }
}
