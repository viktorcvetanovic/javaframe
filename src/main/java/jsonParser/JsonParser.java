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

        var counter = 0;

        if (this.jsonString.charAt(0) != '{' || jsonString.charAt(jsonString.length() - 1) != '}') {
            throw new InvalidJsonFormatException("Your Json is not valid");
        }
        while (true) {
            if (counter == jsonString.length()) {
                break;
            }
            var current = jsonString.charAt(counter++);
            if (current == ' ' || current == '"' || current == '\n') {
                continue;
            }
            if (current == '{') {
                readObject(counter);
            }

            if (current == '[') {
                readArray(counter);
            }
//                throw new InvalidJsonFormatException(String.format("You have error in your Json %s", counter));

        }
        return map;
    }

    private void readArray(int counter) {
        List<Object> list = new ArrayList<>();
        StringBuilder keyBuilder = new StringBuilder();
        while (true) {
            var current = jsonString.charAt(counter++);

            if (current == '"') {
                continue;
            }

            if (current == '{') {
                readObject(counter);
            }
            if (current == ',') {
                list.add(keyBuilder.toString());
                keyBuilder.setLength(0);
                continue;
            }
            if (current == ']') {
                map.put("lista", list);
                break;
            }
            keyBuilder.append(current);
        }
    }


    private void readObject(int counter) {

        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        boolean keyOrValue = true;
        while (true) {
            var current = jsonString.charAt(counter++);

            if (current == '"') {
                continue;
            }
            if (current == ':') {
                keyOrValue = false;
                continue;
            }

            if (current == ',') {
                keyOrValue = true;
                map.put(keyBuilder.toString(), valueBuilder.toString());
                keyBuilder.setLength(0);
                valueBuilder.setLength(0);
                continue;
            }
            if (current == '}') {
                keyOrValue = true;
                map.put(keyBuilder.toString(), valueBuilder.toString());
                keyBuilder.setLength(0);
                valueBuilder.setLength(0);
                break;
            }
            if (current == '{') {
                readObject(counter);
            }
            if (keyOrValue) {
                keyBuilder.append(current);
            } else {
                valueBuilder.append(current);
            }
        }
    }


}
