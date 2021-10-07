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
import util.classutil.ClassUtil;

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
    private final ClassUtil classUtil = new ClassUtil();

    public Object invokeMethodByClass() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Object returnValue = null;
            Class<?> controllerClass = controllerClazz.getClazz();
            Object controllerInstance = classUtil.getBestConstructor(controllerClass).newInstance();
            var method = Arrays.stream(controllerInstance.getClass().getMethods())
                    .filter(Objects::nonNull)
                    .filter(e -> e.isAnnotationPresent(RequestHandler.class))
                    .filter(e -> e.getAnnotation(RequestHandler.class).path().equals(controllerClazz.getMethodPath()))
                    .filter(e -> e.getAnnotation(RequestHandler.class).method() == controllerClazz.getHttpMethod())
                    .findFirst();

            if (method.isPresent()) {
                Set<Object> parameterValues = decideMethodParametersByAnnotation(httpRequest, method.get());
                if (parameterValues.isEmpty()) {
                    returnValue=method.get().invoke(controllerInstance);
                }
                returnValue = method.get().invoke(controllerInstance, parameterValues.toArray());

            }
            return returnValue;
    }


    private Set<Object> decideMethodParametersByAnnotation(HttpRequest httpRequest, Method method) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
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
                if (httpRequest.getPathParams() != null) {
                    var obj = checkIfObjectMatchNameAndTypeForHeader(httpRequest.getPathParams(), params[i].getAnnotation(RequirePath.class).name(), params[i]);
                    setOfParameters.add(obj);
                }
            }
        }
        return setOfParameters;
    }

    private <T> T checkIfObjectMatchTypeForBody(List<HttpKeyValue> body, Parameter parameter) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        System.out.println(body);
        Field[] fields = parameter.getType().getDeclaredFields();
        Object obj = null;
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
