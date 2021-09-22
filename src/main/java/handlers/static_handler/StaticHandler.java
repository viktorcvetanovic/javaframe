package handlers.static_handler;

import data.http.HttpRequest;
import exception.server.FileNotFoundException;
import util.properties.FileFinder;

import java.io.IOException;

//TODO: TO BE IMPLEMENTED
public class StaticHandler {

    public String handle(HttpRequest httpRequest) {
        var fileFinder=new FileFinder();
        try {
            return fileFinder.readFile(fileFinder.findFileByPath(httpRequest.getHttpRequestLine().getPath()));
        } catch (IOException e) {
            throw new FileNotFoundException("File not Found");
        }
    }
}
