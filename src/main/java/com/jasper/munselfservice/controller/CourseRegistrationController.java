package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.course.CourseRegistrationForm;
import com.jasper.munselfservice.entity.Course;
import com.jasper.munselfservice.entity.CourseRegistration;
import com.jasper.munselfservice.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseRegistrationController extends BaseController {
    @PostMapping("/course-registration")
    public ResponseEntity<GenericResponse> courseRegistration(
        @RequestBody CourseRegistrationForm courseRegistrationForm) {


        Student student = getStudentFromSessionId(courseRegistrationForm.getStudentNumber(), courseRegistrationForm.getSessionId());

        if (student == null) {
            return fail("Invalid session");
        }


        if (courseRegistrationForm.getSelectCourse().size() == 0) {
            return fail("No course specified");
        }


        List<CourseRegistration> registrations = new ArrayList<>();
        for (Course course : courseRegistrationForm.getSelectCourse()) {
            CourseRegistration courseRegistration = new CourseRegistration();
            courseRegistration.setCourse(course);
            courseRegistration.setCourseRegistrationTime(new Timestamp(System.currentTimeMillis()));

            Student dummy = new Student();
            dummy.setStudentNumber(courseRegistrationForm.getStudentNumber());
            courseRegistration.setStudent(dummy);

            registrations.add(courseRegistration);
        }

        //不可重复选课
        try {
            courseRegistrationRepository.saveAll(registrations);
        } catch (Exception e) {
            return fail("You have already selected this course");
        }

        return ok();
    }
}
