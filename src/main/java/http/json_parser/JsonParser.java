package http.json_parser;

import util.iterators.StringIterator;

import java.util.*;


public class JsonParser extends StringIterator implements JsonParserInterface {

    private final JsonObject jsonObject = new JsonObject();

    public JsonParser(String data) {
        super(data);
    }


    @Override
    public JsonObject parseJson() {
        eatWhitespace();
        while (hasNext()) {
            Map<String, Object> entry = mapJson();
            if (entry != null) {
                jsonObject.addMap(entry);
            }
        }
        return jsonObject;
    }

    private Map<String, Object> mapJson() {
        eatWhitespace();
        if (isString()) {
            String key;
            Object value;
            next();
            key = eatWhileKey();
            next();
//            if (isValue()) {
            next();
            value = eatWhileValue();
            eatWhitespace();
//            } else {
//                throw new RuntimeException("No value for key at index: " + getIndex());
//            }
            Map<String, Object> map = new HashMap<>();
            map.put(key, value);
            return map;
        }
        eatWhitespace();
        next();
        return null;
    }


    private boolean isString() {
        return peek().equals("\"");
    }

    private String eatWhileKey() {
        return eatWhile(e -> !e.equals("\""));
    }

    private boolean isNumber() {
        return peek().matches("[0-9]");
    }

    private String eatKeyWord() {
        return eatWhile(e -> !e.matches("[,}\\]]"));
    }

    private boolean isObject() {
        return peek().equals("{");
    }

    private boolean isArray() {
        return peek().equals("[");
    }

    private List<Object> eatArray() {
        List<Object> list = new ArrayList<>();
        while (!peek().equals("]")) {
            list.add(mapJson());
            eatWhitespace();
        }
        return list;
    }


    private boolean isValue() {
        return peek().equals(":");
    }

    //switch
    private Object eatWhileValue() {
        eatWhitespace();

        if (isString()) {
            next();
            String value = eatWhile(e -> !e.equals("\""));
            next();
            return value;
        } else if (isNumber()) {
            String strNumber = eatWhile(e -> e.matches("[0-9]"));
            try {
                return Integer.parseInt(strNumber);
            } catch (Exception ex) {
                return Float.parseFloat(strNumber);
            }
        } else if (isObject()) {
            next();
            return mapJson();
        } else if (isArray()) {
            next();
            return eatArray();
        } else {
            String s = eatKeyWord();
            if (s.equals("null")) {
                return null;
            }
            if (s.equals("true")) {
                return true;
            }
            if (s.equals("false")) {
                return false;
            }
        }
        throw new RuntimeException("Character: " + getData().charAt(getIndex()) + " at line: " + getIndex() + " is not valid");
    }

}

