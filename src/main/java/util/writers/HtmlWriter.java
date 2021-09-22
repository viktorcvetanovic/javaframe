package util.writers;

import util.properties.FileFinder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlWriter<T> implements Writer {


    private final FileFinder fileFinder = new FileFinder();
    private final Pattern pattern = Pattern.compile("\\{\\{([a-zA-Z._]+)}}");
    private static final String DIR_NAME = "templates/";
    private InputStream file = null;
    private Map<String, String> data = new HashMap<>();
    private String fileName;


    private String write() throws IOException {
        String fileData = new String(file.readAllBytes());

        StringBuilder builder = new StringBuilder();
        Matcher matcher = pattern.matcher(fileData);

        while (matcher.find()) {
            String key = matcher.group(1);
            if (key != null)
                matcher.appendReplacement(builder, data.get(key));
        }
        matcher.appendTail(builder);
        return builder.toString();
    }


    @Override
    public String writeAndRead() {
        try {
            file = fileFinder.findFileInResourceByName(DIR_NAME + fileName);
            return write();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void setFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setData(String key, String value) {
        data.put(key, value);
    }
}
