package test;

import annotations.Controller;
import annotations.RequestHandler;
import annotations.RequireHeader;
import annotations.RequireJson;
import enums.http.HttpMethod;

import java.util.Arrays;

@Controller(path = "/test")
public class Test {

    @RequestHandler(method = HttpMethod.POST, path = "/viktor")
    public Object dataaaa(@RequireHeader Object a, @RequireJson Object e) {
        return Arrays.asList(a, e);
    }
}
