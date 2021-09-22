package class_finder;


import annotations.Controller;
import data.ControllerClazz;
import enums.http.HttpMethod;
import data.http.HttpRequest;
import exception.controller.ClassNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import registry.ClazzRegistry;

import java.lang.annotation.Annotation;
import java.util.*;

@RequiredArgsConstructor
public class ClassFinder implements ClassFinderInterface {
    private final Reflections reflections = new Reflections("", new SubTypesScanner(false));
    private final ClazzRegistry classRegistry = ClazzRegistry.getInstance();

    @Override
    public ControllerClazz findControllerClassByPathAndMethod(HttpRequest httpRequest) {
        String path = httpRequest.getHttpRequestLine().getPath();
        HttpMethod method = httpRequest.getHttpRequestLine().getMethod();
        Optional<Class<?>> clazz = findFirstControllerClassByPath(path);

        if (clazz.isPresent()) {
            var controllerPath = clazz.get().getAnnotation(Controller.class).path();
            var methodPath = getSplitMethodPath(controllerPath, path);
            return new ControllerClazz(clazz.get(), controllerPath, methodPath, method);
        }
        throw new ClassNotFoundException("Class not found");
    }


    private String getSplitMethodPath(String controllerPath, String httpPath) {
        String[] arrayA = controllerPath.trim().substring(1, controllerPath.length()).split("/");
        String[] arrayB = httpPath.trim().substring(1, httpPath.length()).split("/");
        List<String> listA = new ArrayList<>(Arrays.asList(arrayA));
        List<String> listB = new ArrayList<>(Arrays.asList(arrayB));
        listB.removeAll(listA);
        return "/" + String.join("/", listB);
    }


    public Optional<Class<?>> findFirstClassByAnnotation(Class<? extends Annotation> clazz) {

        return classRegistry.getAllKeys()
                .stream()
                .filter(e -> e.getAnnotation(clazz) != null)
                .findFirst();
    }

    private Optional<Class<?>> findFirstControllerClassByPath(String path) {
        return classRegistry.getAllKeys()
                .stream()
                .filter(e -> e.getAnnotation(Controller.class) != null)
                .filter(e -> path.startsWith(e.getAnnotation(Controller.class).path()))
                .findFirst();
    }

    public Set<Class<?>> findAllLoadedClasses() {
        return reflections.getSubTypesOf(Object.class);
    }
}
