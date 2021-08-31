package class_finder;

import annotations.RequestHandler;
import annotations.RequireHeader;
import annotations.RequireJson;
import data.ControllerClazz;
import enums.http.HttpCode;
import http.data.HttpRequest;
import http.data.HttpResponse;
import http.http_response_builder.HttpResponseFacade;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Data
@AllArgsConstructor
public class ClassHandler {

    private ControllerClazz controllerClazz;
    private HttpRequest httpRequest;

    public Object invokeMethodByClass() {
        Object returnValue = null;
        try {
            Class<?> controllerClass = controllerClazz.getClazz();
            Object controllerInstance = controllerClass.getConstructor().newInstance();
            var method = Arrays.stream(controllerInstance.getClass().getMethods())
                    .filter(Objects::nonNull)
                    .filter(e -> e.isAnnotationPresent(RequestHandler.class))
                    .filter(e -> e.getAnnotation(RequestHandler.class).path().equals(controllerClazz.getMethodPath()))
                    .filter(e -> e.getAnnotation(RequestHandler.class).method() == controllerClazz.getHttpMethod())
                    .findFirst();
            if (method.isPresent()) {
                Annotation[][] annotations = method.get().getParameterAnnotations();
                Set<Object> parameter = decideMethodParametersByAnnotation(httpRequest, annotations);
                returnValue = method.get().invoke(controllerInstance, parameter.toArray());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return returnValue;
    }


    private Set<Object> decideMethodParametersByAnnotation(HttpRequest httpRequest, Annotation[][] annotations) {
        Set<Object> setOfParameters = new HashSet<>();
        for (int i = 0; i < annotations.length; i++) {
            for (int j = 0; j < annotations[0].length; j++) {
                if (annotations[i][j] instanceof RequireJson) {
                    setOfParameters.add(httpRequest.getBody());
                } else if (annotations[i][j] instanceof RequireHeader) {
                    setOfParameters.add(httpRequest.getHeader());
                }
            }
        }
        return setOfParameters;
    }


}
