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

* First thing what you need to do, is to make Main class and config your server.

You can do that by creating a **app.properties** file under resources' dir. Currently, only available configuration is
for server. It should look like this:

```
~pwd: ~/resources/app.properties

server_ip=localhost
server_port=7070
server_backlog=70
```

* Next step is creating main class.

Because of that way of configuration, we can run our entire framework with just one command.

```java
public class Main {
    public static void main(String[] args) {
        ServerMainHandler.run();
    }
}
```

* When you configure your server, JavaFrame is going to handle everything for you. You just need to define controllers
  like I showed here

```java

import annotations.RequestHandler;
import annotations.RequireHeader;
import annotations.RequireJson;
import enums.http.HttpMethod;

@Controller(path = "/test")
public class Test {

    @RequestHandler(method = HttpMethod.POST, path = "/viktor")
    public String dataaaa(@RequireJson(name = "viktor") String viktor, @RequireJson(name = "mare") String mare) {
        System.out.println(mare);
        return viktor;
    }

    //paths are same, but we can know what method will be invoked by HttpMethod(GET,POST...)
    @RequestHandler(method = HttpMethod.GET, path = "/viktor")
    public String data(@RequireHeader(name = "proba") String proba) {
        System.out.println(proba);
        return proba;
    }
}
```
