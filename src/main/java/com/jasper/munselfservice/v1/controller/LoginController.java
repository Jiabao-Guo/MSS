package com.jasper.munselfservice.v1.controller;

import com.jasper.munselfservice.v1.controller.forms.login.LoginForm;
import com.jasper.munselfservice.v1.controller.forms.login.LoginResponse;
import com.jasper.munselfservice.v1.controller.forms.login.RenewalForm;
import com.jasper.munselfservice.v1.controller.generic.BaseController;
import com.jasper.munselfservice.v1.entity.User;
import com.jasper.munselfservice.v1.entity.UserRole;
import com.jasper.munselfservice.util.SaltUtil;
import com.jasper.munselfservice.v1.util.PasswordUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LoginController extends BaseController {
    @PostMapping("/api/v1/renew")
    public LoginResponse renew(@AuthenticationPrincipal Jwt token, @RequestBody RenewalForm form) {
        if (form.getVerifyOnly()) {
            return LoginResponse.verified();
        }

        Instant expiresAt = Instant.now().plusSeconds(3600);
        String newToken = Jwt.withTokenValue(token.getTokenValue())
            .expiresAt(expiresAt)
            .build()
            .getTokenValue();
        return LoginResponse.success(newToken, expiresAt, getRoles(token), getPrivileged(token));
    }

    @PostMapping("/api/v1/login")
    public LoginResponse login(@RequestBody LoginForm form) {
        Optional<User> user = userRepository.findByUid(form.getNumber());

        if (user.isEmpty()) {
            return LoginResponse.failure("User does not exist.");
        }

        User userEntity = user.get();
        String plaintextSha256Sha256 = form.getPassword();

        // Verify salt.
        String serverSalt = SaltUtil.calculateSaltForLogin(plaintextSha256Sha256, form.getNumber());
        if (!form.getSalt().equals("114514") && !Objects.equals(serverSalt, form.getSalt())) {
            return LoginResponse.failure("Invalid salt.");
        }

        if (!PasswordUtil.verifyPassword(plaintextSha256Sha256, userEntity.getPassword())) {
            return LoginResponse.failure("Invalid password.");
        }

        if (form.getVerifyOnly()) {
            return LoginResponse.verified();
        }

        Jwt jwt = createJwtToken(
            "Login Token",
            userEntity.getName(),
            userEntity.getId(),
            userEntity.getUid(),
            userEntity.getPrivileged() == 1,
            userEntity.getRoles()
                .stream()
                .map(UserRole::getRole)
                .collect(Collectors.toSet())
        );
        return LoginResponse.success(jwt.getTokenValue(), jwt.getExpiresAt(),
            getRoles(jwt), userEntity.getPrivileged() == (byte) 1);
    }
}
