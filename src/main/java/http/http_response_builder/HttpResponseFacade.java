package http.http_response_builder;

import enums.http.HttpCode;

import java.util.List;

public class HttpResponseFacade {

    public static String getHttpResponseForJson(HttpCode httpcode, List<Object> list) {
        return HttpResponseBuilder
                .getBuilder()
                .withContentLength(list.toString().length())
                .withBody(list)
                .withHttpCode(httpcode)
                .withContentType("application/json")
                .build();
    }

    public static String getHttpResponseFor404() {
        return HttpResponseBuilder
                .getBuilder()
                .withHttpCode(HttpCode.NOT_FOUND)
                .build();
    }


}
