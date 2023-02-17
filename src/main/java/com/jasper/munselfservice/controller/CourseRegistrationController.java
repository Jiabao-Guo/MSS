package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.course.CourseRegistrationForm;
import com.jasper.munselfservice.entity.Course;
import com.jasper.munselfservice.entity.CourseRegistration;
import com.jasper.munselfservice.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseRegistrationController extends BaseController {
    @PostMapping("/course-registration")
    public ResponseEntity<GenericResponse> courseRegistration(
        @RequestBody CourseRegistrationForm courseRegistrationForm) {

        if (courseRegistrationForm.getCourseNumbers().size() == 0) {
            return fail("No course specified");
        }

        List<CourseRegistration> registrations = new ArrayList<>();
        for (Integer courseNumber : courseRegistrationForm.getCourseNumbers()) {
            CourseRegistration courseRegistration = new CourseRegistration();
            courseRegistration.setCourseNumber(courseNumber);
            courseRegistration.setCourseRegistrationTime(new Timestamp(System.currentTimeMillis()));
            courseRegistration.setStudentNumber(courseRegistrationForm.getStudentNumber());
            registrations.add(courseRegistration);
        }

        try {
            courseRegistrationRepository.saveAll(registrations);
        } catch (Exception e) {
            return fail("You have already selected this course");
        }

        return ok();
    }

    @Transactional
    @DeleteMapping("/course-registration/{id}")
    public ResponseEntity<GenericResponse> deleteCourseRegistration(@PathVariable Integer id) {
        courseRegistrationRepository.deleteById(id);
        return ok();
    }
}
