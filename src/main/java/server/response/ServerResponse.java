package server.response;

import http.http_response_builder.HttpResponseFacade;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Data
@AllArgsConstructor
public class ServerResponse {
    private Socket socket;


     public void writeMessageToServer(String message) {
        try {
            var bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

            bufferedOutputStream.write(message.getBytes(StandardCharsets.UTF_8));
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
