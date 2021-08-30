package server.thread;

import annotations.RequestHandler;
import class_finder.ClassFinder;
import class_finder.ClassFinderInterface;
import class_finder.ClassHandler;
import data.ControllerClazz;
import enums.http.HttpCode;
import http.http_parser.HttpParser;
import http.http_parser.HttpParserInterface;
import http.http_response_builder.HttpResponseFacade;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
            byte[] bytes = new byte[10000];
            bufferedInputStream.read(bytes);
            HttpParserInterface httpParser = new HttpParser();
            var httpRequest = httpParser.parse(bytes);
            ClassFinderInterface classFinderInterface = new ClassFinder();
            //THROW 404
            ControllerClazz classes = classFinderInterface.findClassByPathAndMethod(httpRequest);
            if (classes == null) {
                bufferedOutputStream
                        .write(HttpResponseFacade.getHttpResponseFor404().getBytes(StandardCharsets.UTF_8));
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            } else {
                ClassHandler classHandler = new ClassHandler(classes, httpRequest);
                Object value = classHandler.invokeMethodByClass();
                bufferedOutputStream.write(HttpResponseFacade.getHttpResponseForJson(HttpCode.OK, Arrays.asList(value)).getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
