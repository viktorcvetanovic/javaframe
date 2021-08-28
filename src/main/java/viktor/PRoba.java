package viktor;

import annotations.Controller;
import annotations.RequestHandler;

@Controller(path="/viktor/jebac")
public class PRoba {

    @RequestHandler(path = "/taske")
    public String podatak(){
        return "Bogdan je picka";
    }
}
