package com.jasper.munselfservice.v1.controller.forms.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private Boolean success;
    private String message;
    private String token;
    private Instant expiresAt;
    private Boolean privileged;
    private List<String> roles;

    public static LoginResponse success(String token, Instant expiresAt, List<String> roles, Boolean privileged) {
        return new LoginResponse(true, "Login successful.", token, expiresAt, privileged, roles);
    }

    public static LoginResponse verified() {
        return new LoginResponse(true, "Verified.", null, null, null, null);
    }

    public static LoginResponse failure(String message) {
        return new LoginResponse(false, message, null, null, null, null);
    }
}
