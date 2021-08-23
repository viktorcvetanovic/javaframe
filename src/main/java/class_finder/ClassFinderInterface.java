package class_finder;

import http_parser.data.HttpRequest;

import java.util.Optional;

public interface ClassFinderInterface {
     Optional<Class<?>> findClassByPathAndMethod(HttpRequest httpRequest);
}
