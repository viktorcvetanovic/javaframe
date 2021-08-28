package data;

import enums.http.HttpMethod;
import lombok.*;

import static lombok.AccessLevel.*;

@AllArgsConstructor
@Data
public class ControllerClazz {

    private Class<?> clazz;
    private String controllerPath;
    private String methodPath;
    private HttpMethod httpMethod;

}
