package server.thread;

import handlers.service_handler.ServiceHandler;
import handlers.service_handler.ServiceHandlerImpl;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.Socket;

@Data
@AllArgsConstructor
public class WelcomeThread implements Runnable {
    private Socket socket;

    //TODO : check if it is better to parse http here to know what file we are searching for.
    @Override
    public void run() {
        ServiceHandler serviceHandler = new ServiceHandlerImpl();
        serviceHandler.initialize(socket);
        serviceHandler.handle();
    }


}
