package http.http_parser;

import data.http.HttpRequest;

public interface HttpParserInterface {
     HttpRequest parse(byte[] rawRequest);

}
