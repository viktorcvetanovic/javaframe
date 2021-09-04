package writers;

public interface Writer {

    static <T> Writer initialize(T data) {
        return new HtmlWriter<>(data);
    }

    String writeAndRead();

     void setFile(String fileName);
}
