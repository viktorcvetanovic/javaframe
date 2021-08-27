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
        if (statusCode == HttpCode.NOT_FOUND) {
            return "HTTP/1.1 404 Not Found\n" +
                    "Date:" + now + "\n" +
                    "Content-Type: text/html\n" +
                    "\n" +
                    "<html>\n" +
                    "<head><title>404 Not Found</title></head>\n" +
                    "<body bgcolor=\"white\">\n" +
                    "<center><h1>404 Not Found</h1></center>\n" +
                    "<hr><center>nginx/0.8.54</center>\n" +
                    "</body>\n" +
                    "</html>";
        }
        return httpVersion + " " + statusCode.getCode() + " " + statusCode + "\r\n" +
                "Date: " + now.toString() + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "\r\n" + body;
    }

}
