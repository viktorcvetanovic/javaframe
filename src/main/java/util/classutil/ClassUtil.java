package util.classutil;

import exception.controller.InvalidConstructorException;

import java.lang.reflect.Constructor;
import java.util.*;

public class ClassUtil {

    public Optional<Constructor<?>> getAnyConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors()).findFirst();
    }

    public Constructor<?> getBestConstructor(Class<?> clazz) {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException ex) {
            throw new InvalidConstructorException("Your class" + clazz.getName() + "does not have default constructor");
        }
    }

    public Constructor<?>[] getAllConstructors(Class<?> clazz) {
        return clazz.getConstructors();
    }


}
