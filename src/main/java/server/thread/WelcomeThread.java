package server.thread;

import class_finder.ClassFinder;
import class_finder.ClassFinderInterface;
import http.http_parser.HttpParser;
import http.http_parser.HttpParserInterface;
import http.http_response_builder.HttpResponseFacade;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class WelcomeThread implements Runnable {
    private Socket socket;

    @Override
    public void run() {
        try {
            var is = socket.getInputStream();
            var os = socket.getOutputStream();
            var bufferedOutputStream = new BufferedOutputStream(os);
            var bufferedInputStream = new BufferedInputStream(is);
            byte[] bytes = new byte[1000];
            bufferedInputStream.read(bytes);
            HttpParserInterface httpParser = new HttpParser();
            var httpRequest = httpParser.parse(bytes);
            ClassFinderInterface classFinderInterface = new ClassFinder();
            //THROW 404
            Class<?> classes = classFinderInterface.findClassByPathAndMethod(httpRequest).orElse(null);
            System.out.println(classes);
            if (classes == null) {
                bufferedOutputStream
                        .write(HttpResponseFacade.getHttpResponseForJson(Arrays.asList(1, 2, 3, 4, 5))
                                .getBytes(StandardCharsets.UTF_8));
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
