package class_finder;

import http_parser.data.HttpRequest;

public class ClassFacade {
    HttpRequest httpRequest;

    public ClassFacade(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public <T> T findClassByPathAndMethodForController() {
        return new ClassFinder().findClassByPathAndMethod(httpRequest, "controller");
    }
}
