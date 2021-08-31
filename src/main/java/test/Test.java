package test;

import annotations.Controller;
import annotations.RequestHandler;
import annotations.RequireHeader;
import annotations.RequireJson;
import enums.http.HttpMethod;
import http.data.HttpKeyValue;

import java.util.Arrays;

@Controller(path = "/test")
public class Test {

    @RequestHandler(method = HttpMethod.POST, path = "/viktor")
    public Object dataaaa(@RequireJson(name = "viktor") Integer viktor, @RequireJson(name = "mare") String mare) {
        System.out.println(mare);
        return viktor;
    }
}
