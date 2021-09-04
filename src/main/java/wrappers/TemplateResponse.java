package wrappers;


import writers.Writer;

public class TemplateResponse {

    public <T> String ok(T data, String s) {
        Writer writer = Writer.initialize(data);
        writer.setFile(s);
        return writer.writeAndRead();
    }

}
