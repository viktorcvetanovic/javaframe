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

    @Override
    public void run() {
        ServiceHandler serviceHandler = new ServiceHandlerImpl();
        serviceHandler.initialize(socket);
        serviceHandler.handle();
    }


}
