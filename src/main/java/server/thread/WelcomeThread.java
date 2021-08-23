package server.thread;

import class_finder.ClassFinder;
import class_finder.ClassFinderInterface;
import http.http_parser.HttpParser;
import http.http_parser.HttpParserInterface;
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
            var httpRequest = httpParser.parse(bytes);
            ClassFinderInterface classFinderInterface = new ClassFinder();
            //THROW 404
            Class<?> classes = classFinderInterface.findClassByPathAndMethod(httpRequest).orElse(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
