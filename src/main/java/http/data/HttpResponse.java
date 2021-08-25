package http.data;

import enums.http.HttpCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse {
    private final String httpVersion = "HTTP/1.1";
    private HttpCode statusCode = HttpCode.OK;
    private Integer contentLength = 0;
    private String contentType = "text";
    private LocalDateTime now = LocalDateTime.now();
    private String acceptRange = "bytes";
    private List<Object> body;


    public String returnHttpToString() {
        return httpVersion + " " + statusCode.getCode() + " " + statusCode.toString() + "\n" +
                "Date: " + now.toString() + "\n" +
//                "Content-Length: " + contentLength + "\n" +
                "Content-Type: " + contentType;
    }
}
