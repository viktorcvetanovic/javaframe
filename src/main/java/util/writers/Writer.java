package util.writers;

public interface Writer {

    String writeAndRead();

    void setFile(String fileName);

    void setData(String key,String value);
}
