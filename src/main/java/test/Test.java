package test;

import annotations.*;
import enums.http.HttpMethod;
import wrappers.TemplateResponse;

import java.util.Arrays;

@Controller(path = "/test")
public class Test {
    private final TemplateResponse templateResponse = new TemplateResponse();


    @RequestHandler(method = HttpMethod.GET, path = "/viktor")
    public String dataaaa(@RequirePath(name = "viktor") String viktor) {
        return templateResponse.ok(viktor, "proba.html");
    }

    @RequestHandler(method = HttpMethod.POST, path = "/taske")
    public User proba(@RequireJson(name = "viktor") User user) {
        return user;
    }
}
