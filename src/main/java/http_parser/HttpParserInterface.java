package http_parser;

import http_parser.data.HttpRequest;

public interface HttpParserInterface {
     HttpRequest parse(byte[] rawRequest);

}
