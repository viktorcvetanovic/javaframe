package http.http_response_builder;


import http.data.HttpResponse;

import java.time.LocalDateTime;
import java.util.List;

public class HttpResponseBuilder {

    private HttpResponse httpResponse;

    public static HttpResponseBuilder getBuilder() {
        HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();
        httpResponseBuilder.httpResponse = new HttpResponse();
        return httpResponseBuilder;
    }

    public HttpResponseBuilder withContentLength(Integer contentLength) {
        httpResponse.setContentLength(contentLength);
        return this;
    }

    public HttpResponseBuilder withContentType(String contentType) {
        httpResponse.setContentType(contentType);
        return this;
    }

    public HttpResponseBuilder withTime(LocalDateTime localDateTime) {
        httpResponse.setNow(localDateTime);
        return this;
    }

    public HttpResponseBuilder withAcceptRange(String acceptRange) {
        httpResponse.setAcceptRange(acceptRange);
        return this;
    }

    public HttpResponseBuilder withBody(List<Object> body) {
        httpResponse.setBody(body);
        return this;
    }

    public String build() {
        System.out.println(httpResponse.returnHttpToString());
        return httpResponse.returnHttpToString();
    }
}
