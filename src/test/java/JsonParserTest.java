import json_parser.JsonParser;
import json_parser.JsonParserInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class JsonParserTest {
    private JsonParserInterface jsonParserInterface = new JsonParser();


    @Test
    public void testJsonParserWithObject() {
        var data = "{" + "viktor" + ":" + "car" + "}";
        Map<Object, Object> map = jsonParserInterface.parseJson(data);
        assertEquals("car", String.valueOf(map.get("viktor")));
    }

    @Test
    public void testJsonParserWithArrat() {
        //TODO: implement test
    }


}
