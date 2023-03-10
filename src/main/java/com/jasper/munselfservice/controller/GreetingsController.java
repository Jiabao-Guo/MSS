package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.greetings.GreetingsForm;
import com.jasper.munselfservice.controller.forms.greetings.GreetingsResponse;
import com.jasper.munselfservice.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GreetingsController extends BaseController {
    @RequestMapping("/greetings")
    public ResponseEntity<GreetingsResponse> greetings(@RequestBody GreetingsForm entity) {
        Student student = getStudentFromSessionId(entity.getStudentNumber(), entity.getSessionId());

        if (student == null) {
            return ResponseEntity.ok(
                new GreetingsResponse("invalid student number", 0));
        }
        return ResponseEntity.ok(
            new GreetingsResponse(student.getStudentName(), student.getId()));
    }
}
