package class_finder;

import annotations.RequestHandler;
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
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

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
                //TODO: check annotation for paramethers and give them that type of data
                Annotation[][] paramethers = method.get().getParameterAnnotations();
                System.out.println(httpRequest.getBody());
                returnValue = method.get().invoke(controllerInstance, httpRequest.getBody());

            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return returnValue;
    }


}
