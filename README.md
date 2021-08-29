# JAVA FRAME

### Java framework made for fun and learning.

Everything in JavaFrame is custom and made by me. From parsing request to returning results.

Still in development, but very fast growing project. At this moment available features are:

* Parsing json
* Parsing http
* Returning http
* Adding controller annotations and method annotations
* Invoking methods

Things that need to be developed:

* Working with Database
* Security
* Generating controllers and entities by DDL

This is example how you should use framework:

* First thing what you need to do, is to make Main class and config your server. Currently, configuration is done by
  class.

```java
public class Main {
    public static void main(String[] args) {
        ServerMainHandler.Config.setBackLog(0);
        ServerMainHandler.Config.setInetAdress("0.0.0.0");
        ServerMainHandler.Config.setServerPort(7070);
        ServerMainHandler.run();
    }
}
```

* When you configure your server, JavaFrame is going to handle everything for you. You just need to define controllers
  like I showed here

```java

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
```
