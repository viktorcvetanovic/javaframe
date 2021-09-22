package util.properties;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileFinder {

    public InputStream findFileInResourceByName(String name) {
        return FileFinder.class.getClassLoader().getResourceAsStream(name);
    }

    public File findFileByPath(String path) {
        return new File(path);
    }

    public String readFile(File file) throws IOException {
        return Files.readString(Paths.get(file.getPath()));
    }
}
