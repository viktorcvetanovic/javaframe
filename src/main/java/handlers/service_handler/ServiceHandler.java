package handlers.service_handler;

import class_finder.ClassFinder;
import class_finder.ClassFinderInterface;
import class_finder.ClassHandler;
import data.ControllerClazz;
import data.http.HttpRequest;
import lombok.Data;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

@Data
public class ServiceHandler {


    public Object handle(HttpRequest httpRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        ClassFinderInterface classFinderInterface = new ClassFinder();
        ControllerClazz classes = classFinderInterface.findClassByPathAndMethod(httpRequest);
        ClassHandler classHandler = new ClassHandler(classes, httpRequest);
        return classHandler.invokeMethodByClass();
    }




}
