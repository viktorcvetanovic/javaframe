package httpParser;

import httpParser.data.HttpRequest;

public interface HttpParserInterface {
     HttpRequest parse(byte[] rawRequest);

}
