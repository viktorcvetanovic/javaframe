package data.http;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private List<HttpKeyValue> header;
    private List<HttpKeyValue> body;
    private List<HttpKeyValue> pathParams;

}
