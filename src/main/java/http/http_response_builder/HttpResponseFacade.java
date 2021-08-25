package http.http_response_builder;

import java.util.List;

public class HttpResponseFacade {

    public static String getHttpResponseForJson(List<Object> list) {
        return HttpResponseBuilder
                .getBuilder()
                .withContentLength(list.toString().length())
                .withBody(list)
                .withContentType("application/json")
                .build();
    }

}
