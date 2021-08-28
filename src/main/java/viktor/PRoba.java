package viktor;

import annotations.Controller;
import annotations.RequestHandler;

@Controller(path = "/viktor/car")
public class PRoba {

    @RequestHandler(path = "/taske")
    public String podatak() {
        return "Bogdan je car";
    }
}
