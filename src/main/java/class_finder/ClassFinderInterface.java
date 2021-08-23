package class_finder;

import http.data.HttpRequest;

import java.util.Optional;

public interface ClassFinderInterface {
     Optional<Class<?>> findClassByPathAndMethod(HttpRequest httpRequest);
}
