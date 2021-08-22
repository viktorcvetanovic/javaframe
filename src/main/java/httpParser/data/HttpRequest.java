package httpParser.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private List<HttpHeader> header;
    private List<HttpHeader> body;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static final class HttpRequestLine {
        private String method;
        private String path;
        private String version;
    }

    @Data
    @AllArgsConstructor
    @ToString
    public static final class HttpHeader {
        private String key;
        private String value;
    }
}
