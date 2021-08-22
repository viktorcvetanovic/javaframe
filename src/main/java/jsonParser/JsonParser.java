package jsonParser;

import exception.json.InvalidJsonFormatException;
import lombok.NonNull;

import java.util.*;


public class JsonParser implements JsonParserInterface {

    private String jsonString;
    private Map<Object, Object> map = new HashMap<>();


    @Override
    public Map<Object, Object> parseJson(@NonNull String json) {
        this.jsonString = json.trim();
        List<Object> list=new ArrayList<>();
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        var counter = 0;
        boolean keyOrValue = true;
        if (this.jsonString.charAt(0) != '{' || jsonString.charAt(jsonString.length() - 1) != '}') {
            throw new InvalidJsonFormatException("Invalid JSON format, please check it");
        }
        while (true) {
            if (counter == jsonString.length()) {
                break;
            }
            var current = jsonString.charAt(counter++);
            if (current == ' ' || current == '"' || current == '{' || current == '\n') {
                continue;
            }
            if(current=='['){
            }
            if(current==']'){
            }
            if (current == ':') {
                keyOrValue = false;
                continue;
            }
            if (current == ',' || current == '}') {
                keyOrValue = true;
                map.put(keyBuilder.toString(), valueBuilder.toString());
                keyBuilder.setLength(0);
                valueBuilder.setLength(0);
                continue;
            }
            if (keyOrValue) {
                keyBuilder.append(current);
            } else {
                valueBuilder.append(current);
            }
        }
        return map;
    }

    private void readObject(){

    }




}
