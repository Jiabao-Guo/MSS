package com.example.munselfservice.controller.forms;

import com.example.munselfservice.entity.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseRegistrationForm {

    //根据前段post的位置  handleRegistrator()
    // 编写出三个属性

    private Integer studentNumber;
    private String sessionId;

    private List<Course> selectCourse;


}
