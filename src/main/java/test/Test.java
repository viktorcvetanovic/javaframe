package test;

import annotations.Controller;
import annotations.RequestHandler;
import annotations.RequireHeader;
import annotations.RequireJson;
import enums.http.HttpMethod;
import wrappers.TemplateResponse;

import java.util.Arrays;

@Controller(path = "/test")
public class Test {
    private final TemplateResponse templateResponse = new TemplateResponse();


    @RequestHandler(method = HttpMethod.GET, path = "/viktor")
    public String dataaaa(@RequireHeader(name = "viktor") String viktor) {
        return templateResponse.ok(viktor, "proba.html");
    }
}
