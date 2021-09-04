package class_finder;

import data.ControllerClazz;
import data.http.HttpRequest;

public interface ClassFinderInterface {
    ControllerClazz findClassByPathAndMethod(HttpRequest httpRequest);
}
