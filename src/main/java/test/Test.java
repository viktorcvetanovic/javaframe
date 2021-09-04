package test;

import annotations.Controller;
import annotations.RequestHandler;
import annotations.RequireJson;
import enums.http.HttpMethod;
import wrappers.TemplateResponse;

@Controller(path = "/test")
public class Test {
    private final TemplateResponse templateResponse = new TemplateResponse();


    @RequestHandler(method = HttpMethod.POST, path = "/viktor")
    public String dataaaa(@RequireJson(name = "viktor") String viktor) {
        System.out.println(templateResponse.ok(viktor, "proba.html"));
        return viktor;
    }
}
