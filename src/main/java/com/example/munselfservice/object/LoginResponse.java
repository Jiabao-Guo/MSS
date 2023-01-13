package com.example.munselfservice.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private Boolean isSuccess;
    private String message;
    private String sessionId;
}
