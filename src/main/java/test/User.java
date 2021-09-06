package test;

import annotations.Writtable;
import lombok.Data;

@Data
@Writtable
public class User {
    private String username;
    private String password;
}
