package com.example.munselfservice.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    private Integer number_123;
    private String password;
    private Boolean isStudentLogin;
}
