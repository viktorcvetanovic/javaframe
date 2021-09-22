package wrappers;


import util.writers.HtmlWriter;
import util.writers.Writer;


public class TemplateResponse {
    private final Writer htmlWriter = new HtmlWriter();

    public String ok(String file) {
        htmlWriter.setFile(file);
        return htmlWriter.writeAndRead();
    }

    public void setData(String key, String value) {
        htmlWriter.setData(key, value);
    }

}
