package util.null_checker;

public class NullUtil {

    public static <T> T orElseGet(T object, T def) {
        if (object == null) {
            return def;
        }
        return object;
    }
}
