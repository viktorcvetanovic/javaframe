package class_finder;

import enums.http.HttpMethod;
import http_parser.data.HttpRequest;

public interface ClassFinderInterface {
    void findClassByPathAndMethod(HttpRequest httpRequest);
}
