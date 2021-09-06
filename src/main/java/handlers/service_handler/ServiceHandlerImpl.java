package handlers.service_handler;

import class_finder.ClassFinder;
import class_finder.ClassFinderInterface;
import class_finder.ClassHandler;
import data.ControllerClazz;
import http.http_parser.HttpParser;
import http.http_parser.HttpParserInterface;
import http.http_response_builder.HttpResponseFacade;
import lombok.Data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Data
public class ServiceHandlerImpl implements ServiceHandler {
    private Socket socket;
    private BufferedOutputStream bufferedOutputStream;
    private BufferedInputStream bufferedInputStream;
    private byte[] bytes = new byte[1000];


    @Override
    public void handle() {
        HttpParserInterface httpParser = new HttpParser();
        var httpRequest = httpParser.parse(bytes);
        ClassFinderInterface classFinderInterface = new ClassFinder();
        ControllerClazz classes = classFinderInterface.findClassByPathAndMethod(httpRequest);
        if (classes == null) {
            writeMessageToServer(bufferedOutputStream, HttpResponseFacade.getHttpResponseFor404());
            return;
        }
        ClassHandler classHandler = new ClassHandler(classes, httpRequest);
        Object value = null;
        value = classHandler.invokeMethodByClass();
        writeMessageToServer(bufferedOutputStream, HttpResponseFacade.getHttpResponseForHtml((String) value));
//        writeMessageToServer(bufferedOutputStream, HttpResponseFacade.getHttpResponseForJson(HttpCode.OK, Arrays.asList(value)));
    }

    @Override
    public void initialize(Socket socket) {

        try {
            bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            bufferedInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeMessageToServer(BufferedOutputStream bufferedOutputStream, String message) {
        try {
            bufferedOutputStream.write(message.getBytes(StandardCharsets.UTF_8));
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
