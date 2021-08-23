package http.http_parser;

import http.data.HttpRequest;

public interface HttpParserInterface {
     HttpRequest parse(byte[] rawRequest);

}
