package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.entity.CourseRegistration;
import com.jasper.munselfservice.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
public class StudentController extends BaseController {
    @GetMapping("/student/{id}/course-registration")
    public Set<CourseRegistration> courseRegistration(@PathVariable Integer id) {
        try {
            return courseRegistrationRepository.findAllByStudent_Id(id);
        }
        catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}
