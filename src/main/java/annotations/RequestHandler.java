package annotations;

import enums.http.HttpMethod;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestHandler {
    String path();

    HttpMethod method();
}
