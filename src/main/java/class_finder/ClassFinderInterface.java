package class_finder;

import enums.http.HttpMethod;
import http_parser.data.HttpRequest;

public interface ClassFinderInterface {
    <T> T findClassByPathAndMethod(HttpRequest httpRequest,String packageName);
}
