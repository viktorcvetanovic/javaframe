package enums.http;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HttpCode {
    OK("200"), INTERNAL_SERVER_CODE("500"), NOT_FOUND("404");

    public final String code;

    public String getCode() {
        return code;
    }

}
