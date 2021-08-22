package httpParser;

import exception.http.InvalidHttpRequestLineException;
import httpParser.data.HttpRequest;
import httpParser.data.HttpRequest.HttpHeader;
import jsonParser.JsonParser;
import jsonParser.JsonParserInterface;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpParser implements HttpParserInterface {
    private String requestString;
    private static final int REQUEST_LINE_PARAMETER_COUNT = 3;
    private int bodyIndex;

    @Override
    public HttpRequest parse(byte[] rawRequest) {
        requestString = new String(rawRequest);
        var requestLine = getRequestLine();
        var headerArray = getHeaderArray();
        String body = getBody();
        JsonParserInterface jsonParserInterface = new JsonParser();
        return new HttpRequest(mapRequestLineFromStringArray(requestLine),
                mapHttpJsonFromStringArray(headerArray),
                mapMapToHttpHeader(jsonParserInterface.parseJson(body)));
    }


    private String[] getHeaderArray() {
        String[] httpRequest = requestString.split("\r\n");
        if (checkIfHttpHasBody()) {
            for (int i = 0; i < httpRequest.length; i++) {
                var item = httpRequest[i];
                if (item == null || item.equals("")) {
                    bodyIndex = i;
                    return Arrays.copyOfRange(httpRequest, 1, i);
                }
            }
        }
        return Arrays.copyOfRange(httpRequest, 1, httpRequest.length);
    }

    private String getBody() {
        String[] httpRequest = requestString.split("\r\n");
        return httpRequest[httpRequest.length - 1];
    }

    private String[] getRequestLine() {
        String[] httpRequestLine = requestString.split("\r\n")[0].split(" ");
        if (httpRequestLine.length != REQUEST_LINE_PARAMETER_COUNT) {
            throw new InvalidHttpRequestLineException("Your http request line doesnt have standard length");
        }
        return httpRequestLine;
    }

    private boolean checkIfHttpHasBody() {
        String[] httpRequest = requestString.split("\r\n");
        for (String item : httpRequest) {
            if (item == null || item.equals("") || item.equals("\r\n")) {
                return true;
            }
        }
        return false;
    }


    private HttpRequest.HttpRequestLine mapRequestLineFromStringArray(@NonNull String[] array) {
        if (array.length == 0) {
            return new HttpRequest.HttpRequestLine();
        }
        return new HttpRequest.HttpRequestLine(array[0], array[1], array[2]);
    }

    private List<HttpHeader> mapHttpJsonFromStringArray(@NonNull String[] array) {
        if (array.length == 0)
            return new ArrayList<>() {
            };
        return Arrays.stream(array).map(e ->
                new HttpHeader(e.split(":")[0], e.split(":")[1])).collect(Collectors.toList());
    }

    private List<HttpHeader> mapMapToHttpHeader(Map<Object, Object> map) {
        List<HttpHeader> list = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            list.add(new HttpHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
        }
        return list;
    }

}
