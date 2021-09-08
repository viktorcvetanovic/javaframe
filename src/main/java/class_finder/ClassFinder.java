package class_finder;


import annotations.Controller;
import data.ControllerClazz;
import enums.http.HttpMethod;
import data.http.HttpRequest;
import exception.controller.ClassNotFoundException;
import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import util.classutil.ClassUtil;

import java.util.*;

@Getter
public class ClassFinder implements ClassFinderInterface {
    private final ClassUtil classUtil = new ClassUtil();

    @Override
    public ControllerClazz findClassByPathAndMethod(HttpRequest httpRequest) {
        String path = httpRequest.getHttpRequestLine().getPath();
        HttpMethod method = httpRequest.getHttpRequestLine().getMethod();
        Optional<Class<?>> clazz = classUtil.findFirstClassByAnnotationAndPath(Controller.class, path);

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


}
