package com.jasper.munselfservice.v1.controller;

import com.jasper.munselfservice.v1.controller.forms.greetings.GreetingsResponse;
import com.jasper.munselfservice.v1.controller.generic.BaseController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GreetingsController extends BaseController {
    @RequestMapping("/api/v1/greetings")
    public GreetingsResponse hello(@AuthenticationPrincipal Jwt token) {
        return new GreetingsResponse(getName(token), getUserUid(token));
    }
}
