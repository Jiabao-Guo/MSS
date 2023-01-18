package com.example.munselfservice.controller;

import com.example.munselfservice.entity.Course;
import com.example.munselfservice.entity.CourseRegistration;
import com.example.munselfservice.entity.Student;
import com.example.munselfservice.controller.forms.CourseRegistrationForm;
import com.example.munselfservice.controller.forms.CourseRegistrationResponse;
import com.example.munselfservice.repository.CourseRegistrationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseRegistrationController extends BaseController{
    private final CourseRegistrationRepository courseRegistrationRepository;

    public CourseRegistrationController(CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    @RequestMapping("/course-registration")
    public ResponseEntity<CourseRegistrationResponse> courseRegistration(
            @RequestBody CourseRegistrationForm courseRegistrationForm){


        Student student = validateStudentSession(courseRegistrationForm.getStudentNumber(), courseRegistrationForm.getSessionId());

        if (student == null) {
            return ResponseEntity.ok(
                    new CourseRegistrationResponse(false,"no student, can not select course"));
        }


        if(courseRegistrationForm.getSelectCourse().size() == 0) {
            return ResponseEntity.ok(
                    new CourseRegistrationResponse(false,"Please choose at least one course"));

        }



        List<CourseRegistration> registrations = new ArrayList<>();
        for (Course course: courseRegistrationForm.getSelectCourse()) {
            CourseRegistration courseRegistration = new CourseRegistration();
            courseRegistration.setCourse(course);

            courseRegistration.setCourseRegistrationTime(new Timestamp(System.currentTimeMillis()));

            Student student1 = new Student();
            student1.setStudentNumber(courseRegistrationForm.getStudentNumber());
            courseRegistration.setStudent(student1);

            registrations.add(courseRegistration);
        }

        //不可重复选课
        try {
            courseRegistrationRepository.saveAll(registrations);
        }catch (Exception e){
            return ResponseEntity.ok(
                    new CourseRegistrationResponse(false,"do not choose the same course"));
        }


        return ResponseEntity.ok(
                new CourseRegistrationResponse(true, "you select successfully, and have " +
                        courseRegistrationForm.getSelectCourse().size() + "courses"));
    }
}
