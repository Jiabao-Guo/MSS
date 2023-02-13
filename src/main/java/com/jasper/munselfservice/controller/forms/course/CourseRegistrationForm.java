package com.jasper.munselfservice.controller.forms.course;

import com.jasper.munselfservice.entity.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseRegistrationForm {
    private Integer studentNumber;
    private String sessionId;
    private List<Course> selectCourse;
}
