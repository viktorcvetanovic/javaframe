package class_finder;

import data.ControllerClazz;
import http.data.HttpRequest;

import java.util.Optional;

public interface ClassFinderInterface {
    ControllerClazz findClassByPathAndMethod(HttpRequest httpRequest);
}
