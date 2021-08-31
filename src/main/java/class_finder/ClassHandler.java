package class_finder;

import annotations.RequestHandler;
import annotations.RequireHeader;
import annotations.RequireJson;
import data.ControllerClazz;
import enums.http.HttpCode;
import exception.controller.InvalidParameterClassOrJsonData;
import http.data.HttpKeyValue;
import http.data.HttpRequest;
import http.data.HttpResponse;
import http.http_response_builder.HttpResponseFacade;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

                Set<Object> parameterValues = decideMethodParametersByAnnotation(httpRequest, method.get());
                returnValue = method.get().invoke(controllerInstance, parameterValues.toArray());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return returnValue;
    }


    private Set<Object> decideMethodParametersByAnnotation(HttpRequest httpRequest, Method method) {
        Annotation[][] annotations = method.getParameterAnnotations();
        Set<Object> setOfParameters = new HashSet<>();
        for (int i = 0; i < annotations.length; i++) {
            for (int j = 0; j < annotations[0].length; j++) {
                if (annotations[i][j] instanceof RequireJson) {
                    var obj = checkIfObjectMatchNameAndType(httpRequest.getBody(), ((RequireJson) annotations[i][j]).name(), method.getParameters());
                    setOfParameters.add(obj);
                } else if (annotations[i][j] instanceof RequireHeader) {
                    var obj = checkIfObjectMatchNameAndType(httpRequest.getHeader(), ((RequireHeader) annotations[i][j]).name(), method.getParameters());
                    setOfParameters.add(obj);
                }
            }
        }
        return setOfParameters;
    }


    private <T> T checkIfObjectMatchNameAndType(List<HttpKeyValue> list, String nameOfField, Parameter... parameters) {
        for (Parameter p : parameters) {
            for (HttpKeyValue value : list) {
                if (value.getKey().equals(nameOfField)) {
                    try {
                        var obj = p.getType().cast(value.getValue());
                        return (T) obj;
                    } catch (Exception ex) {
                        throw new InvalidParameterClassOrJsonData("Your method " + p + " has error for parsing " + nameOfField + " field, check your type");
                    }
                }
            }
        }
        return null;
    }

}
