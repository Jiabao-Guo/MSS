package com.jasper.munselfservice.v1.controller;

import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.controller.forms.course.CourseRegistrationForm;
import com.jasper.munselfservice.v1.controller.generic.BaseController;
import com.jasper.munselfservice.v1.entity.Course;
import com.jasper.munselfservice.v1.entity.CourseRegistration;
import com.jasper.munselfservice.v1.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseRegistrationController extends BaseController {
    @PostMapping("/api/v1/course-registration")
    public ResponseEntity<GenericResponse> courseRegistration(
        @AuthenticationPrincipal Jwt token,
        @RequestBody CourseRegistrationForm form
    ) {

        if (form.getCourseNumbers().size() == 0) {
            return fail("No course specified");
        }

        List<CourseRegistration> registrations = new ArrayList<>();
        List<Course> courses = courseRepository.findAllByCourseNumberIn(form.getCourseNumbers());

        for (int i = 0; i < form.getCourseNumbers().size(); ++i) {
            CourseRegistration registration = CourseRegistration.builder()
                .userId(getUserId(token))
                .courseId(courses.get(i).getId())
                .registrationTime(new Timestamp(System.currentTimeMillis()))
                .build();

            registrations.add(registration);
        }

        try {
            courseRegistrationRepository.saveAll(registrations);
        } catch (Exception e) {
            return fail("Some courses are already registered.");
        }

        return ok();
    }


    @GetMapping("/api/v1/course-registration")
    public Collection<CourseRegistration> retrieveMyCourseRegistration(
        @AuthenticationPrincipal Jwt token) {
        Integer id = getUserId(token);
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getCourseRegistrations).orElse(null);
    }


    @Transactional
    @DeleteMapping("/api/v1/course-registration/{id}")
    public ResponseEntity<GenericResponse> deleteCourseRegistration(@PathVariable Integer id) {
        courseRegistrationRepository.deleteById(id);
        return ok();
    }
}
