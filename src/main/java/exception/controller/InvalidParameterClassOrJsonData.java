package exception.controller;

public class InvalidParameterClassOrJsonData extends RuntimeException{

    public InvalidParameterClassOrJsonData(String message) {
        super(message);
    }
}
