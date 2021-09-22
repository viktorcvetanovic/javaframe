package data.http;

import enums.http.HttpCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse {
    private final String httpVersion = "HTTP/1.1";
    private HttpCode statusCode = HttpCode.OK;
    private Integer contentLength = 0;
    private String contentType = "text/html";
    private LocalDateTime now = LocalDateTime.now();
    private String acceptRange = "bytes";
    private String body;


    public String returnHttpToString() {
        return httpVersion + " " + statusCode.getCode() + " " + statusCode + "\r\n" +
                "Date: " + now.toString() + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "\r\n" + body;
    }

}
