package com.jasper.munselfservice.controller.forms.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private Boolean success;
    private String message;
    private String sessionId;
}
