package server.thread;

import httpParser.HttpParser;
import httpParser.HttpParserInterface;
import httpParser.data.HttpRequest;
import jsonParser.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.net.Socket;

@Data
@AllArgsConstructor
public class WelcomeThread implements Runnable {
    private Socket socket;

    @Override
    public void run() {
        try {
            var is = socket.getInputStream();
            byte[] bytes = is.readAllBytes();
            HttpParserInterface httpParser = new HttpParser();
            var httpRequest=httpParser.parse(bytes);

            System.out.println(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
