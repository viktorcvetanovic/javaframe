package jsonParser;

import java.util.Map;

public interface JsonParserInterface {
    Map<Object,Object> parseJson(String json);
}
