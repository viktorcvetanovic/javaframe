package test;

import annotations.Controller;
import annotations.RequestHandler;
import annotations.RequireJson;
import enums.http.HttpMethod;
import http.data.HttpRequest;
import http.http_response_builder.HttpResponseBuilder;

@Controller(path = "/test")
public class Test {

    @RequestHandler(method = HttpMethod.POST, path = "/viktor")
    public Object dataaaa(@RequireJson Object e) {
        return e;
    }
}
