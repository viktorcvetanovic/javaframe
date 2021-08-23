package class_finder;


import annotations.Controller;
import http.data.HttpRequest;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.*;


public class ClassFinder implements ClassFinderInterface {

    @Override
    public Optional<Class<?>> findClassByPathAndMethod(HttpRequest httpRequest) {
        String path = httpRequest.getHttpRequestLine().getPath();
        var reflections = new Reflections("", new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(e -> e.getAnnotation(Controller.class) != null)
                .filter(e -> path.startsWith(e.getAnnotation(Controller.class).path()))
                .findFirst();
    }
}
