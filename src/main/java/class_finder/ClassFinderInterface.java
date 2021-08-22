package class_finder;

import enums.http.HttpMethod;

public interface ClassFinderInterface {
    void findClassByPathAndMethod(String path, HttpMethod method);
}
