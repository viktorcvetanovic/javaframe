package http.http_parser;

import enums.http.HttpMethod;
import exception.http.InvalidHttpRequestLineException;

import data.http.HttpKeyValue;
import data.http.HttpRequest;
import data.http.HttpRequestLine;
import http.json_parser.JsonParser;
import http.json_parser.JsonParserInterface;
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
            JsonParserInterface jsonParserInterface = new JsonParser(body);
            return new HttpRequest(mapRequestLineFromStringArray(requestLine),
                    mapHttpJsonFromStringArray(headerArray),
                    mapMapToHttpHeader(jsonParserInterface.parseJson().getMap()), mapPathParamsFromRequestLine(requestLine));
        }
        return new HttpRequest(mapRequestLineFromStringArray(requestLine),
                mapHttpJsonFromStringArray(headerArray), null, mapPathParamsFromRequestLine(requestLine));
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
        String[] httpRequestLine = requestString.trim().split("\r\n")[0].split(" ");
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
            httpRequestLine = new HttpRequestLine(HttpMethod.valueOf(array[0]), array[1].split("\\?")[0], array[2]);
        } catch (Exception ex) {
            throw new InvalidHttpRequestLineException("Your Http Method is not valid");
        }
        return httpRequestLine;
    }

    private List<HttpKeyValue> mapPathParamsFromRequestLine(String[] httpRequestLine) {
        String[] request = httpRequestLine[1].split("\\?");
        if (request.length <= 1) {
            return null;
        }
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        List<HttpKeyValue> list = new ArrayList<>();
        //TODO : NEED TO BE SPLITED WITH & AND THEN GET KEY AND VALUE WITH =...
        String[] params = request[1].split("=");
        for (int i = 0; i < params.length; i++) {
            var param = params[i];
            if (i % 2 == 0) {
                key.append(param);
            } else {
                value.append(param);
                list.add(new HttpKeyValue(key.toString(), value.toString()));
                key.setLength(0);
                value.setLength(0);
            }
        }
        return list;
    }

    private List<HttpKeyValue> mapHttpJsonFromStringArray(@NonNull String[] array) {
        if (array.length == 0)
            return new ArrayList<>() {
            };
        return Arrays.stream(array).map(e ->
                new HttpKeyValue(e.split(":")[0], e.split(":")[1])).collect(Collectors.toList());
    }

    private List<HttpKeyValue> mapMapToHttpHeader(Map<String, Object> map) {
        List<HttpKeyValue> list = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            list.add(new HttpKeyValue(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
        }
        return list;
    }

}
