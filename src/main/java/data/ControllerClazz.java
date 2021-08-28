package data;

import lombok.*;

import static lombok.AccessLevel.*;

@AllArgsConstructor
@Data
public class ControllerClazz {

    private Class<?> clazz;
    private String controllerPath;
    private String methodPath;

}
