package util.writers;

import annotations.Writtable;
import http.http_response_builder.HttpResponseBuilder;
import util.iterators.StringIterator;
import util.properties.FileFinder;

import java.io.*;
import java.util.List;

public class HtmlWriter<T> implements Writer {


    private final HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();
    private final FileFinder fileFinder = new FileFinder();

    private static final String DIR_NAME = "templates/";
    private InputStream file = null;
    private static final String[] symbolToReplace = new String[]{"{{", "}}"};
    private T data;
    private String fileName;

    public HtmlWriter(T data) {
        this.data = data;
    }

    //TODO: TO BE IMPLEMENTED
    private String write(T data) throws IOException {
        String fileData = new String(file.readAllBytes());
        boolean isObjectAnnotated = data.getClass().isAnnotationPresent(Writtable.class) || (data instanceof List && ((List<?>) data).get(0).getClass().isAnnotationPresent(Writtable.class));

        if (isObjectAnnotated) {
            System.out.println("cao");
        } else {
            fileData = fileData.replaceAll("\\{.*?\\}}", (String) data);
        }
        return fileData;
    }

    @Override
    public String writeAndRead() {
        try {
            file = fileFinder.findFileByName(DIR_NAME + fileName);
            return write(data);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void setFile(String fileName) {
        this.fileName = fileName;
    }
}
