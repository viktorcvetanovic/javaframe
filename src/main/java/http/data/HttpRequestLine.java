package http.data;


import enums.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpRequestLine {
    private HttpMethod method;
    private String path;
    private String version;
}
