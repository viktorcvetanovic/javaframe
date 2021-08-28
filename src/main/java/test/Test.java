package test;

import annotations.Controller;
import annotations.RequestHandler;
import enums.http.HttpMethod;

@Controller(path = "/test")
public class Test {

    @RequestHandler(path = "/viktor", method = HttpMethod.GET)
    public String hej() {
        return "Cao Viktore";
    }

    @RequestHandler(path = "/viktor", method = HttpMethod.POST)
    public String heje() {
        return "Cao Viktore POST";
    }

    @RequestHandler(path = "/toma", method = HttpMethod.PUT)
    public String heja() {
        return "Cao Tomislave";
    }
}
