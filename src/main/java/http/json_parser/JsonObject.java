package http.json_parser;


import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class JsonObject {

    private Map<String, Object> map = new HashMap<>();

    void setPair(String key, Object value) {
        this.map.put(key, value);
    }

    void addMap(Map<String, Object> newMap) {
        newMap.forEach((key, value) -> map.put(key, value));
    }


    public Map<String,Object> getMap(){
        return map;
    }


}
