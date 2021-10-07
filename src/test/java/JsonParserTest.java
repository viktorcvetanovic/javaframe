import http.json_parser.JsonParser;
import http.json_parser.JsonParserInterface;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class JsonParserTest {
//resiti problem ako su dva kljuca s istim imenom
    //problem kod parsovanja liste objekata

    @Test
    public void testJsonParserWithString() {
        var data = "{" + "\"viktor\"" + ":" + "\"car\"" + "," + "\"viktor1\"" + ":" + "\"car\"" + "," + "\"viktor2\"" + ":" + "\"car\"" + "}";
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
        var data = "{" + "\"true\"" + ":" + "true" + "," + "\"false\"" + ":" + "false" + "}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        assertEquals(true, map.get("true"));
        assertEquals(false, map.get("false"));
        System.out.println(map);
    }


    @Test
    public void testJsonParserWithBooleanAndString() {
        var data = "{" + "\"true\"" + ":" + "true" + "," + "\"false\"" + ":" + "\"aaaa\"" + "}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        assertEquals(true, map.get("true"));
        assertEquals("aaaa", map.get("false"));
        System.out.println(map);
    }


    @Test
    public void testJsonParserWithObject() {
        var data = "{" + "\"true\"" + ":" + "true" + "," + "\"obj\"" + ":" + "{" + "\"kayn\"" + ":" + "\"proba\""+ "," +"\"aaafsafsa\"" +":"+ "{"+"\"onako\"" +":"+"5" +"}" + "}" +"\"obj2\"" + ":" + "{" + "\"kayn\"" + ":" + "\"proba\"" + "}" + "}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        Map<String, Object> testMap = Map.of("kayn", "proba");
        assertEquals(testMap, map.get("obj"));
        assertEquals(true, map.get("true"));
        System.out.println(map);
    }


    @Test
    public void testJsonParserWithArray() {
        var data = "{" + "\"true\"" + ":" + "true" + "," + "\"obj\"" + ":" + "[" + "\"kayn\"" + "," + "\"proba\"" + "]" + "}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        System.out.println(map);
    }

    @Test
    public void testJsonParserWithArrayOfObject() {
        var data = "{" + "\"true\"" + ":" + "true" + "," + "\"obj\"" + ":" + "[" + "{" + "\"kayn\"" + ":" + "\"proba\"" + "," + "\"viki\"" + ":" + "\"aaaaaa\"" + "}"+"," + "{" + "\"kayn\"" + ":" + "\"proba\"" + "}" + "]" + "}";
        JsonParserInterface jsonParserInterface = new JsonParser(data);
        Map<String, Object> map = jsonParserInterface.parseJson().getMap();
        System.out.println(map);
    }


}
