package class_finder;

import http_parser.data.HttpRequest;

public class ClassFinderFacade {
    HttpRequest httpRequest;

    public ClassFinderFacade(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public <T> T findClassByPathAndMethodForController() {
        return new ClassFinder().findClassByPathAndMethod(httpRequest, "controller");
    }
}
