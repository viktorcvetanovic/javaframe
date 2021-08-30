package http.http_parser;

import enums.http.HttpMethod;
import exception.http.InvalidHttpRequestLineException;

import http.data.HttpKeyValue;
import http.data.HttpRequest;
import http.data.HttpRequestLine;
import json_parser.JsonParser;
import json_parser.JsonParserInterface;
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
        String body = getBody(mapRequestLineFromStringArray(requestLine));
        if (body != null) {
            JsonParserInterface jsonParserInterface = new JsonParser();
            return new HttpRequest(mapRequestLineFromStringArray(requestLine),
                    mapHttpJsonFromStringArray(headerArray),
                    mapMapToHttpHeader(jsonParserInterface.parseJson(body)));
        }
        return new HttpRequest(mapRequestLineFromStringArray(requestLine),
                mapHttpJsonFromStringArray(headerArray), null);
    }


    private String[] getHeaderArray() {
        String[] httpRequest = requestString.split("\r\n");
        for (int i = 0; i < httpRequest.length; i++) {
            var item = httpRequest[i];
            if (item == null || item.equals("")) {
                bodyIndex = i;
                return Arrays.copyOfRange(httpRequest, 1, i);
            }
        }
        return Arrays.copyOfRange(httpRequest, 1, httpRequest.length);
    }

    private String getBody(HttpRequestLine requestLine) {
        if (requestLine.getMethod() == HttpMethod.GET) {
            return null;
        }
        String[] httpRequest = requestString.split("\r\n\r\n");
        return httpRequest[httpRequest.length - 1];
    }

    private String[] getRequestLine() {
        String[] httpRequestLine = requestString.split("\r\n")[0].split(" ");
        if (httpRequestLine.length != REQUEST_LINE_PARAMETER_COUNT) {
            throw new InvalidHttpRequestLineException("Your http request line doesnt have standard length");
        }
        return httpRequestLine;
    }

    private HttpRequestLine mapRequestLineFromStringArray(@NonNull String[] array) {
        if (array.length == 0) {
            return new HttpRequestLine();
        }
        HttpRequestLine httpRequestLine = null;
        try {
            httpRequestLine = new HttpRequestLine(HttpMethod.valueOf(array[0]), array[1], array[2]);

        } catch (Exception ex) {
            throw new InvalidHttpRequestLineException("Your Http Method is not valid");
        }
        return httpRequestLine;
    }

    private List<HttpKeyValue> mapHttpJsonFromStringArray(@NonNull String[] array) {
        if (array.length == 0)
            return new ArrayList<>() {
            };
        return Arrays.stream(array).map(e ->
                new HttpKeyValue(e.split(":")[0], e.split(":")[1])).collect(Collectors.toList());
    }

    private List<HttpKeyValue> mapMapToHttpHeader(Map<Object, Object> map) {
        List<HttpKeyValue> list = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            list.add(new HttpKeyValue(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
        }
        return list;
    }

}
