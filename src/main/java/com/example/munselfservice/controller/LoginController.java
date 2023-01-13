package com.example.munselfservice.controller;

import com.example.munselfservice.entity.Student;
import com.example.munselfservice.object.LoginForm;
import com.example.munselfservice.object.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LoginController extends BaseController {

//    通过basecontroller继承  可省略
//    @Resource
//    private StudentRepository studentRepository;

    @RequestMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginForm entity) {
        if (!entity.getIsStudentLogin()) {
            return ResponseEntity.ok(
                new LoginResponse(false, "Please login as a student.", null));
                   /**             isSucess ,      message,                               sessionId */
        }

        /**
         *
         */
        Student student = studentRepository.findByStudentNumber(entity.getNumber_123());

        if (student == null){
            return ResponseEntity.ok(
                    new LoginResponse(false, "user invalid.", null));

        }

        if (student.getPassword().equals(entity.getPassword()) ) {
            return ResponseEntity.ok(
                new LoginResponse(
                        true,
                        "Login success.",
                        generateSessionId(student.getStudentNumber()
                                )));
        }

        return ResponseEntity.ok(
            new LoginResponse(false, "Invalid password.", null));
    }

    /**
     * 生成随机SessionID（也就是一个随机字符串）作为手环
     * 然后保存到服务端的session id map中，同时返回给前端用户
     * @return session id
     */
    private String generateSessionId(Integer studentNumber) {
        // 生成随机字符串作为session id
        String sessionId = UUID.randomUUID().toString();

        // 存入redis
        redisTemplate
                .opsForValue()
                .set(studentNumber, sessionId);

        return sessionId;
    }
}
