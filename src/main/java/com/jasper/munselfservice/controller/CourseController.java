package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.entity.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController extends BaseController{

    @GetMapping("/course")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
}
