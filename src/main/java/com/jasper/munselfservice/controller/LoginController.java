package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.login.LoginForm;
import com.jasper.munselfservice.controller.forms.login.LoginResponse;
import com.jasper.munselfservice.entity.Student;
import com.jasper.munselfservice.util.HashUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LoginController extends BaseController {
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginForm entity) {
        if (!entity.getIsStudentLogin()) {
            return ResponseEntity.ok(
                new LoginResponse(false, "Please login as a student.", null));
        }

        Student student = studentRepository.findByStudentNumber(entity.getNumber());

        if (student == null) {
            return ResponseEntity.ok(
                new LoginResponse(false, "user invalid.", null));
        }

        String entityPasswordSha256Sha256 = HashUtil.sha256(entity.getPasswordSha256());

        if (student.getPasswordSha256Sha256().equals(entityPasswordSha256Sha256)) {
            return ResponseEntity.ok(
                new LoginResponse(
                    true,
                    "Login success.",
                    generateSessionId(student.getStudentNumber()
                    )
                )
            );
        }

        return ResponseEntity.ok(
            new LoginResponse(false, "Invalid password.", null));
    }

    private String generateSessionId(Integer studentNumber) {
        String sessionId = UUID.randomUUID().toString();
        redisTemplate
            .opsForValue()
            .set(studentNumber, sessionId);
        return sessionId;
    }
}
