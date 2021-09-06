package data.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class HttpKeyValue {
    private String key;
    private Object value;
}