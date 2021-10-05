package handlers.http_handler;

import data.http.HttpRequest;
import exception.server.InternalServerError;
import http.http_parser.HttpParser;
import http.http_parser.HttpParserInterface;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

public class HttpHandler {

    public HttpRequest readHttpRequest(Socket socket) {
        byte[] bytes = new byte[1000];
        try {
            var bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            bufferedInputStream.read(bytes);
        } catch (IOException e) {
            throw new InternalServerError("There was error while trying to read your http request");
        }
        HttpParserInterface httpParser = new HttpParser();
        return httpParser.parse(bytes);
    }
}
