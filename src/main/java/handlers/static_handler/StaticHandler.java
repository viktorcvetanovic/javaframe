package handlers.static_handler;

import data.http.HttpRequest;
import util.properties.FileFinder;

import java.io.IOException;

//TODO: TO BE IMPLEMENTED
public class StaticHandler {

    public String handle(HttpRequest httpRequest) throws IOException {
        var fileFinder=new FileFinder();
        return fileFinder.readFile(fileFinder.findFileByPath(httpRequest.getHttpRequestLine().getPath()));
    }
}
