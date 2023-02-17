package com.jasper.munselfservice.controller.forms.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    private Integer number;
    private String passwordSha256;
    private Boolean isStudentLogin;
}
