package handlers.static_handler;

import config.Config;
import data.http.HttpRequest;
import exception.server.FileNotFoundException;
import util.properties.FileFinder;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class StaticHandler {
    private Config config = Config.getInstance();

    public String handle(HttpRequest httpRequest) {
        var fileFinder = new FileFinder();
        try {
            if (config.getServerStaticContent().equals("static")) {
                return fileFinder.readFile(fileFinder
                        .findFileByPath(new LinkedList<String>(Arrays.asList(httpRequest.getHttpRequestLine().getPath().split("/")))
                                .getLast()));
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File not Found");
        }
        return null;
    }
}
