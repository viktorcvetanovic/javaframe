package annotations;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePath {
    String name() default "";
}
