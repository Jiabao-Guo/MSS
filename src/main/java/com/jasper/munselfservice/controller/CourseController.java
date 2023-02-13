package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.course.CourseForm;
import com.jasper.munselfservice.entity.Course;
import com.jasper.munselfservice.entity.Student;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseController extends BaseController{

    @RequestMapping("/course")
    public List<Course> getCourses(@RequestBody CourseForm courseForm) {
        Student student = getStudentFromSessionId(
            courseForm.getStudentNumber(),
            courseForm.getSessionId()
        );

        if (student == null) {
            return new ArrayList<>();
        }

        return courseRepository.findAll();
    }
}
