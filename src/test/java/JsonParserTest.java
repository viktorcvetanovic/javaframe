import http.json_parser.JsonObject;
import http.json_parser.JsonParser;
import http.json_parser.JsonParserInterface;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonParserTest {


    @Test
    public void testJsonParserWithString() {
        var data = "{" + "\"viktor\"" + ":" + "\"car\"" + "}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        assertEquals("car", map.get("viktor"));
        System.out.println(map);
    }


    @Test
    public void testJsonParserWithNumber() {
        var data = "{" + "\"broj\"" + ":" + "5" + "}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        assertEquals(5, map.get("broj"));
        System.out.println(map);
    }


    @Test
    public void testJsonParserWithNull() {
        var data = "{" + "\"null\"" + ":" + "null" + "}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        assertNull(map.get("null"));
        System.out.println(map);
    }


    @Test
    public void testJsonParserWithBoolean() {
        var data = "{" + "\"true\"" + ":" + "true" + ","+"\"false\""+":"+"false"+"}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        assertEquals(true, map.get("true"));
        assertEquals(false,map.get("false"));
        System.out.println(map);
    }



    @Test
    public void testJsonParserWithBooleanAndString() {
        var data = "{" + "\"true\"" + ":" + "true" + ","+"\"false\""+":"+"\"aaaa\""+"}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        assertEquals(true, map.get("true"));
        assertEquals("aaaa",map.get("false"));
        System.out.println(map);
    }


    @Test
    public void testJsonParserWithObject(){
        var data = "{" + "\"true\"" + ":" + "true" + ","+"\"obj\""+":"+"{"+"\"kayn\""+ ":"+"\"proba\"" +"}"+"}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        Map<String,Object> testMap=Map.of("kayn","proba");
        assertEquals(testMap,map.get("obj"));
        assertEquals(true,map.get("true"));
        System.out.println(map);
    }



    @Test
    public void testJsonParserWithArray(){
        var data = "{" + "\"true\"" + ":" + "true" + ","+"\"obj\""+":"+"["+"\"kayn\""+ ","+"\"proba\"" +"]"+"}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        System.out.println(map);
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
