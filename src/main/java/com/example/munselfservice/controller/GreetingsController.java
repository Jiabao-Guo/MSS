package com.example.munselfservice.controller;

import com.example.munselfservice.entity.Student;
import com.example.munselfservice.controller.forms.GreetingsForm;
import com.example.munselfservice.controller.forms.GreetingsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GreetingsController extends BaseController{

//    @Resource
//    private StudentRepository studentRepository;


    @RequestMapping("/greetings")
    public ResponseEntity<GreetingsResponse> greetings(@RequestBody GreetingsForm entity) {

        /**
         entity.getStudentNumber(), entity.getSessionId() 从 前端 取得
         */
        Student student = validateStudentSession(entity.getStudentNumber(), entity.getSessionId());

        if (student == null) {
            return ResponseEntity.ok(
                new GreetingsResponse("invalid student number"));
        }
        return ResponseEntity.ok(
            new GreetingsResponse(student.getStudentName()));
    }


}
