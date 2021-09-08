package util.classutil;

import annotations.Controller;
import exception.controller.InvalidConstructorException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class ClassUtil {
    private final Reflections reflections = new Reflections("", new SubTypesScanner(false));

    public Optional<Class<?>> findFirstClassByAnnotation(Class<?> clazz) {

        return reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(e -> e.getAnnotation(Controller.class) != null)
                .findFirst();
    }

    public Optional<Class<?>> findFirstClassByAnnotationAndPath(Class<?> clazz, String path) {
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(e -> e.getAnnotation(Controller.class) != null)
                .filter(e -> path.startsWith(e.getAnnotation(Controller.class).path()))
                .findFirst();
    }

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

    public Set<Class<?>> getAllLoadedClasses() {
        return reflections.getSubTypesOf(Object.class);
    }
}
