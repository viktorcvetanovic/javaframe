import json_parser.JsonParser;
import json_parser.JsonParserInterface;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
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
    public void testJsonParserWithArray() {
        var data = "{list:[" + "viktor" + "," + "car" + "]}";
        Map<Object, Object> map = jsonParserInterface.parseJson(data);
        List<String> list = (List<String>) map.get("list");
        assertEquals("viktor", list.get(0));
        assertEquals("car", list.get(1));
    }

//

//    @Test
//    public void testJsonParserWithArray2() {
//        var data = "{list:[" + "viktor" + "," + "car" + "]}";
//        Map<Object, Object> map = jsonParserInterface.parseJson(data);
//        List<String> list = (List<String>) map.get("list");
//        assertEquals("viktor", list.get(0));
//        assertEquals("car", list.get(1));
//    }

}
