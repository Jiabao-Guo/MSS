package com.jasper.munselfservice.v1.controller.forms.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    private Integer number;
    private String password;
    private Boolean verifyOnly;
    private String salt;
}
