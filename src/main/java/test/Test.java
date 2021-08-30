package test;

import annotations.Controller;
import annotations.RequestHandler;
import annotations.RequireHeader;
import annotations.RequireJson;
import enums.http.HttpMethod;

@Controller(path = "/test")
public class Test {

    @RequestHandler(method = HttpMethod.POST, path = "/viktor")
    public Object dataaaa(@RequireJson Object e, @RequireHeader Object a) {
        return e;
    }
}
