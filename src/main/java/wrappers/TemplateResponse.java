package wrappers;


import lombok.Data;
import lombok.NoArgsConstructor;
import writers.Writer;

@Data
@NoArgsConstructor
public class TemplateResponse {

    public <T> String ok(T viktor, String s) {
        Writer writer = Writer.initialize(viktor);
        writer.setFile(s);
        return writer.writeAndRead();
    }

}
