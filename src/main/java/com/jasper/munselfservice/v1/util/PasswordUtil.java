package com.jasper.munselfservice.v1.util;

import com.jasper.munselfservice.util.HashUtil;
import com.jasper.munselfservice.v1.controller.forms.login.LoginResponse;

import java.util.Objects;
import java.util.UUID;

public class PasswordUtil {
    public static Boolean verifyPassword(String plaintextSha256Sha256, String realPasswordSalted) {
        // pass = salt + h(h(h(plaintext)) + salt)
        String saltExtracted = realPasswordSalted.substring(0, 64);
        String passwordRecovered = saltExtracted + HashUtil.sha256(plaintextSha256Sha256 + saltExtracted);
        return Objects.equals(passwordRecovered, realPasswordSalted);
    }

    public static String createPassword(String plaintext) {
        // pass = salt + h(h(h(plaintext)) + salt)
        String plaintextSha256Sha256 = HashUtil.sha256(HashUtil.sha256(plaintext));
        String salt = UUID.randomUUID().toString().replace("-", "")
            + UUID.randomUUID().toString().replace("-", "");
        return salt + HashUtil.sha256(plaintextSha256Sha256 + salt);
    }
}
