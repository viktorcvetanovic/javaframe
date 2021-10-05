package server.thread;

import data.http.HttpRequest;
import exception.controller.ClassNotFoundException;
import exception.server.FileNotFoundException;
import handlers.http_handler.HttpHandler;
import handlers.service_handler.ServiceHandler;
import handlers.static_handler.StaticHandler;
import http.http_response_builder.HttpResponseFacade;
import lombok.AllArgsConstructor;
import server.response.ServerResponse;

import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Objects;

@AllArgsConstructor
public class WelcomeThread implements Runnable {
    private Socket socket;

    @Override
    public void run() {
        ServerResponse serverResponse = new ServerResponse(socket);
        HttpHandler httpHandler = new HttpHandler();
        HttpRequest httpRequest = httpHandler.readHttpRequest(socket);
        ServiceHandler serviceHandler = new ServiceHandler();
        StaticHandler staticHandler = new StaticHandler();
        try {
            Object value = serviceHandler.handle(httpRequest);
            if (value == null) {
                value = staticHandler.handle(httpRequest);
            }
            serverResponse.writeMessageToServer(HttpResponseFacade.getHttpResponseForHtml(value.toString()));
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            serverResponse.writeMessageToServer(Objects.requireNonNull(HttpResponseFacade.getHttpResponseForException(e.getMessage())));
        } catch (ClassNotFoundException | FileNotFoundException ex) {
            serverResponse.writeMessageToServer(HttpResponseFacade.getHttpResponseFor404(ex.getMessage()));
        }
    }

}
