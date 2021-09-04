package properties;

import java.io.InputStream;

public class FileFinder {
    public InputStream findFileByName(String name) {
        return FileFinder.class.getClassLoader().getResourceAsStream(name);
    }
}
