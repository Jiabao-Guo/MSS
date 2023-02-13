package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.course.CourseQueryForm;
import com.jasper.munselfservice.entity.Course;
import com.jasper.munselfservice.util.NumericUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class CourseController extends BaseController{
    @GetMapping("/course")
    public Page<Course> retrieve(CourseQueryForm form) {
        Integer courseNumber = NumericUtil.parseIntOrDefault(form.getCourseNumber(), 0);

        Page<Course> coursesByCourseNumber = courseRepository.findByCourseNumberEquals(
            PageRequest.of(form.getPage(), form.getAmount()),
            courseNumber
        );

        if (coursesByCourseNumber.getTotalElements() > 0) {
            return coursesByCourseNumber;
        }

        return courseRepository.findByNameContainingAndInstructorByInstructorNumber_NameContaining(
            PageRequest.of(form.getPage(), form.getAmount()),
            form.getName(),
            form.getInstructorName()
        );
    }
}
