package http.json_parser;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class JsonObject {

    private Map<String, Object> map = new HashMap<>();

    public void setPair(String key, Object value) {
        this.map.put(key, value);
    }

    public void addMap(Map<String, Object> newMap) {
        newMap.forEach((key, value) -> map.put(key, value));
    }


}
