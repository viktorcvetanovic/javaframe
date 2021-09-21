package util.properties;

import java.io.File;
import java.io.InputStream;

public class FileFinder {

    public InputStream findFileInResourceByName(String name) {
        return FileFinder.class.getClassLoader().getResourceAsStream(name);
    }

    public File findFileByPath(String path){
        return new File(path);
    }
}
