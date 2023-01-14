package com.example.munselfservice.controller;

import com.example.munselfservice.entity.Student;
import com.example.munselfservice.object.CourseForm;
import com.example.munselfservice.object.CourseRegistrationForm;
import com.example.munselfservice.object.CourseRegistrationResponse;
import com.example.munselfservice.object.GreetingsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseRegistrationController extends BaseController{

    @RequestMapping("/course-registration")
    public ResponseEntity<CourseRegistrationResponse> courseRegistration(
            @RequestBody CourseRegistrationForm courseRegistrationForm){


        Student student = validateStudentSession(courseRegistrationForm.getStudentNumber(), courseRegistrationForm.getSessionId());

        if (student == null) {
            return ResponseEntity.ok(
                    new CourseRegistrationResponse(false,"no student, can not select course"));
        }
        return ResponseEntity.ok(
                new CourseRegistrationResponse(true,"you select successfully, and have "+
                        courseRegistrationForm.getSelectCourse().size()+ "courses"));




    }



}
