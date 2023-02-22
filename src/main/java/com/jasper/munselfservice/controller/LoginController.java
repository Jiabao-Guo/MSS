package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.login.LoginForm;
import com.jasper.munselfservice.controller.forms.login.LoginResponse;
import com.jasper.munselfservice.entity.Student;
import com.jasper.munselfservice.util.HashUtil;
import com.jasper.munselfservice.util.SaltUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
public class LoginController extends BaseController {
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginForm form) {
        if (!form.getStudentLogin()) {
            return ResponseEntity.ok(
                new LoginResponse(false, "Please login as a student.", null));
        }

        Student student = studentRepository.findByStudentNumber(form.getNumber());

        if (student == null) {
            return ResponseEntity.ok(
                new LoginResponse(false, "User does not exist.", null));
        }

        String plaintextSha256Sha256 = form.getPasswordSha256Sha256();
        // Verify salt.
        String serverSalt = SaltUtil.calculateSaltForLogin(
            plaintextSha256Sha256, form.getNumber(), form.getStudentLogin());
        if (!Objects.equals(serverSalt, form.getSalt())) {
            return ResponseEntity.ok(
                new LoginResponse(false, "Invalid salt.", null));
        }

        // pass = salt + h(h(h(plaintext)) + salt)
        String realPasswordSalted = student.getPassword();
        String saltExtracted = realPasswordSalted.substring(0, 64);
        String passwordRecovered = saltExtracted + HashUtil.sha256(plaintextSha256Sha256 + saltExtracted);

        // Verify password.
        if (!Objects.equals(passwordRecovered, realPasswordSalted)) {
            return ResponseEntity.ok(
                new LoginResponse(false, "Invalid password.", null));
        }


        String sessionId;
        if (form.getVerifyOnly()) {
            sessionId = null;
        }
        else {
            sessionId = generateSessionId(student.getStudentNumber());
        }

        return ResponseEntity.ok(
            new LoginResponse(true, "Login success.", sessionId));
    }

    private String generateSessionId(Integer studentNumber) {
        String sessionId = UUID.randomUUID().toString();
        redisTemplate
            .opsForValue()
            .set(studentNumber, sessionId);
        return sessionId;
    }
}
