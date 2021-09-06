package server.thread;

import annotations.RequestHandler;
import class_finder.ClassFinder;
import class_finder.ClassFinderInterface;
import class_finder.ClassHandler;
import data.ControllerClazz;
import enums.http.HttpCode;
import exception.controller.InvalidParameterClassOrJsonData;
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
            ControllerClazz classes = classFinderInterface.findClassByPathAndMethod(httpRequest);
            if (classes == null) {
                writeMessageToServer(bufferedOutputStream, HttpResponseFacade.getHttpResponseFor404());
            } else {
                ClassHandler classHandler = new ClassHandler(classes, httpRequest);
                Object value = null;
                try {
                    value = classHandler.invokeMethodByClass();
                    writeMessageToServer(bufferedOutputStream, HttpResponseFacade.getHttpResponseForHtml((String) value));
                } catch (Exception ex) {
                    writeMessageToServer(bufferedOutputStream, HttpResponseFacade.getHttpResponseForJson(HttpCode.OK, Arrays.asList(value)));
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


    private void writeMessageToServer(BufferedOutputStream bufferedOutputStream, String message) throws IOException {
        bufferedOutputStream.write(message.getBytes(StandardCharsets.UTF_8));
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

}
