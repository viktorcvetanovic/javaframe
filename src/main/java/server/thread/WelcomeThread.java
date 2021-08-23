package server.thread;

import class_finder.ClassFinder;
import class_finder.ClassFinderInterface;
import http_parser.HttpParser;
import http_parser.HttpParserInterface;
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
            var classes = classFinderInterface.findClassByPathAndMethod(httpRequest).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
