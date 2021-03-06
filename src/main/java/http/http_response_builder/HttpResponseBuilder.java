package http.http_response_builder;


import enums.http.HttpCode;
import data.http.HttpResponse;

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
        httpResponse.setBody(body.toString());
        return this;
    }

    public HttpResponseBuilder withBody(String body) {
        httpResponse.setBody(body.toString());
        return this;
    }

    public HttpResponseBuilder withHttpCode(HttpCode httpCode) {
        httpResponse.setStatusCode(httpCode);
        return this;
    }

    public String build() {

        return httpResponse.returnHttpToString();
    }
}
