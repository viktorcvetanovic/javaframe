package server.thread;

import data.http.HttpRequest;
import handlers.http_handler.HttpHandler;
import handlers.service_handler.ServiceHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import server.response.ServerResponse;

import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

@Data
@AllArgsConstructor
public class WelcomeThread implements Runnable {
    private Socket socket;
    private final ServerResponse serverResponse = new ServerResponse(socket);

    @Override
    public void run() {
        HttpHandler httpHandler = new HttpHandler();
        HttpRequest httpRequest = httpHandler.readHttpRequest(socket);

        ServiceHandler serviceHandler = new ServiceHandler();
        try {
            Object value = serviceHandler.handle(httpRequest);
            serverResponse.writeMessageToServer(value.toString());
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            serverResponse.writeMessageToServer(e.getMessage());
        }
    }

}
