package server.thread;

import data.http.HttpRequest;
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
            Object serviceValue = serviceHandler.handle(httpRequest);
            staticHandler.handle(httpRequest);
            if (serviceValue == null) {
                serverResponse.writeMessageToServer(HttpResponseFacade.getHttpResponseFor404());
            }
            serverResponse.writeMessageToServer(HttpResponseFacade.getHttpResponseForHtml(serviceValue.toString()));
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            serverResponse.writeMessageToServer(Objects.requireNonNull(HttpResponseFacade.getHttpResponseForException(e.getMessage())));
        }
    }

}
