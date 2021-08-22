package class_finder;

import enums.http.HttpMethod;
import http_parser.data.HttpRequest;

public class ClassFinder implements ClassFinderInterface {

    @Override
    public <T> T findClassByPathAndMethod(HttpRequest httpRequest,String packageName) {
        return null;
    }
}
