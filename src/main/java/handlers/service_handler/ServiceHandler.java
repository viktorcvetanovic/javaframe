package handlers.service_handler;

import java.net.Socket;

public interface ServiceHandler {
    void initialize(Socket socket);
     void handle();
}
