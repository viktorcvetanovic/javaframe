package class_finder;


import annotations.Controller;
import data.ControllerClazz;
import enums.http.HttpMethod;
import data.http.HttpRequest;
import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.*;

@Getter
public class ClassFinder implements ClassFinderInterface {


    @Override
    public ControllerClazz findClassByPathAndMethod(HttpRequest httpRequest) {
        String path = httpRequest.getHttpRequestLine().getPath();
        HttpMethod method = httpRequest.getHttpRequestLine().getMethod();
        var reflections = new Reflections("", new SubTypesScanner(false));
        Optional<Class<?>> clazz = reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(e -> e.getAnnotation(Controller.class) != null)
                .filter(e -> path.startsWith(e.getAnnotation(Controller.class).path()))
                .findFirst();
        if (clazz.isPresent()) {
            var controllerPath = clazz.get().getAnnotation(Controller.class).path();
            var methodPath = getSplitMethodPath(controllerPath, path);
            return new ControllerClazz(clazz.get(), controllerPath, methodPath, method);
        }
        return null;
    }


    private String getSplitMethodPath(String controllerPath, String httpPath) {
        String[] arrayA = controllerPath.trim().substring(1, controllerPath.length()).split("/");
        String[] arrayB = httpPath.trim().substring(1, httpPath.length()).split("/");
        List<String> listA = new ArrayList<>(Arrays.asList(arrayA));
        List<String> listB = new ArrayList<>(Arrays.asList(arrayB));
        listB.removeAll(listA);
        return "/" + String.join("/", listB);
    }


}
