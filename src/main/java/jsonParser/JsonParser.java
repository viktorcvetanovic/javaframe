package jsonParser;

import lombok.NonNull;

import java.util.*;


public class JsonParser implements JsonParserInterface {

    private String jsonString;
    private Map<Object, Object> map = new HashMap<>();
    private int counter = 0;

    @Override
    public Map<Object, Object> parseJson(@NonNull String json) {
        this.jsonString = json.trim();

//        if ((this.jsonString.charAt(1) != '{' && jsonString.charAt(jsonString.length() - 2) != '}') || (this.jsonString.charAt(1) != '[' && jsonString.charAt(jsonString.length() - 2) != ']')) {
//            throw new InvalidJsonFormatException("Your Json is not valid");
//        }
        while (true) {
            if (counter == jsonString.length()) {
                break;
            }
            var current = jsonString.charAt(counter++);
            if (current == ' ' || current == '\n' || current == ',' || current == '"') {
                continue;
            }
            if (current == '{') {
                readObject();
            }

            if (current == '[') {
                readArray();
            }


//                throw new InvalidJsonFormatException(String.format("You have error in your Json %s", counter));
        }
        return map;
    }

    private void readArray() {
        List<Object> list = new ArrayList<>();
        StringBuilder keyBuilder = new StringBuilder();
        while (true) {
            var current = jsonString.charAt(counter++);
            if (current == '"') {
                continue;
            }
            if (current == '{') {
                readObject();
                continue;
            }
            if (current == ',') {
                list.add(keyBuilder.toString());
                keyBuilder.setLength(0);
                continue;
            }
            if (current == ']') {
                if (!keyBuilder.toString().equals("")) {
                    list.add(keyBuilder.toString());
                }
                keyBuilder.setLength(0);
                map.put("lista" + counter, list);
                break;
            }
            keyBuilder.append(current);
        }
    }


    private void readObject() {
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        boolean keyOrValue = true;
        while (true) {
            var current = jsonString.charAt(counter++);

            if (current == '[') {
                keyBuilder.setLength(0);
                valueBuilder.setLength(0);
                readArray();
                continue;
            }
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
                readObject();
                continue;
            }
            if (keyOrValue) {
                keyBuilder.append(current);
            } else {
                valueBuilder.append(current);
            }
        }
    }


}
