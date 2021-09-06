package class_finder;

import annotations.RequestHandler;
import annotations.RequireHeader;
import annotations.RequireJson;
import annotations.RequirePath;
import data.ControllerClazz;
import exception.controller.InvalidParameterClassOrJsonData;
import data.http.HttpKeyValue;
import data.http.HttpRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
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
            //TODO: CHANGE TO GET ANY CONSTRUCTOR
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
        Parameter[] params = method.getParameters();
        Set<Object> setOfParameters = new HashSet<>();
        for (int i = 0; i < params.length; i++) {
            if (params[i].isAnnotationPresent(RequireJson.class)) {
                var obj = checkIfObjectMatchTypeForBody(httpRequest.getBody(), params[i]);
                setOfParameters.add(obj);
            } else if (params[i].isAnnotationPresent(RequireHeader.class)) {
                var obj = checkIfObjectMatchNameAndTypeForHeader(httpRequest.getHeader(), params[i].getAnnotation(RequireHeader.class).name(), params[i]);
                setOfParameters.add(obj);
            } else if (params[i].isAnnotationPresent(RequirePath.class)) {
                //TODO: TO BE IMPLEMENTED
                return null;
            }
        }
        return setOfParameters;
    }

    private <T> T checkIfObjectMatchTypeForBody(List<HttpKeyValue> body, Parameter parameter) {
        Field[] fields = parameter.getType().getDeclaredFields();
        Object obj = null;
        try {
            obj = parameter.getType().getDeclaredConstructor().newInstance();
            for (Field f : fields) {
                for (HttpKeyValue value : body) {
                    if (value.getKey().equals(f.getName())) {
                        f.setAccessible(true);
                        f.set(obj, value.getValue());
                        f.setAccessible(false);
                    }
                }

            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return (T) obj;
    }


    private <T> T checkIfObjectMatchNameAndTypeForHeader(List<HttpKeyValue> list, String nameOfField, Parameter parameter) {
        for (HttpKeyValue value : list) {
            if (value.getKey().equals(nameOfField)) {
                try {
                    var obj = parameter.getType().cast(value.getValue());
                    return (T) obj;
                } catch (Exception ex) {
                    throw new InvalidParameterClassOrJsonData("Your method " + parameter + " has error for parsing " + nameOfField + " field, check your type");
                }

            }
        }
        return null;
    }

}
