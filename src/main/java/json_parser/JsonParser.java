package json_parser;

import exception.json.InvalidJsonFormatException;
import lombok.NonNull;

import java.util.*;


public class JsonParser implements JsonParserInterface {

    private String jsonString;
    private Map<Object, Object> map = new HashMap<>();
    private Iterator<Character> iterator;

    @Override
    public Map<Object, Object> parseJson(@NonNull String json) {
        this.jsonString = json.trim();
        validateJson();
        var keyOrValue = true;
        var keyBuilder = new StringBuilder();
        var valueBuilder = new StringBuilder();
        var state = 0;
        List<Object> list = new ArrayList<>();
        //glavna logika mora da se odvija ovde, mora da postoje metode koje ce da vracaju elemente koji se dodaju u Mapu u zavisnosti koji je tip.
        initializeIterator();
        while (iterator.hasNext()) {
            var current = iterator.next();
            if (current == ' ' || current == '"') {
                continue;
            }

            if (current == '{') {
                state = 0;
                continue;
            }
            if (current == ':') {
                keyOrValue = false;
                continue;
            }
            if (current == '[') {
                state = 1;
                continue;
            }


            if (state == 0) {
                if (current == '}') {
                    map.put(keyBuilder.toString(), valueBuilder.toString());
                    state = 1;
                    continue;
                }
                if (current == ',') {
                    keyOrValue = true;
                    if (!keyBuilder.toString().equals("")) {
                        map.put(keyBuilder.toString(), valueBuilder.toString());
                    }
                    valueBuilder.setLength(0);
                    keyBuilder.setLength(0);
                    continue;
                }
            }
            if (state == 1) {
                if (current == ',') {
                    list.add(valueBuilder.toString());
                    valueBuilder.setLength(0);
                    continue;
                }
                if (current == ']') {
                    list.add(valueBuilder.toString());
                    map.put(keyBuilder.toString(), list);
                    valueBuilder.setLength(0);
                    keyBuilder.setLength(0);
                    list = new ArrayList<>();
                    state = 0;
                    continue;
                }
            }
            if (keyOrValue) {
                keyBuilder.append(current);
            } else {
                valueBuilder.append(current);
            }
        }

        return map;
    }


    private void validateJson() {
        if ((this.jsonString.charAt(0) != '{' || jsonString.charAt(jsonString.length() - 1) != '}')
                && (this.jsonString.charAt(0) != '[' || jsonString.charAt(jsonString.length() - 1) != ']')) {
            throw new InvalidJsonFormatException("Your Json is not valid");
        }
    }

    private void initializeIterator() {
        List<Character> characterList = new ArrayList<>();
        for (char c : this.jsonString.toCharArray()) {
            characterList.add(c);
        }
        this.iterator = characterList.iterator();
    }


}
