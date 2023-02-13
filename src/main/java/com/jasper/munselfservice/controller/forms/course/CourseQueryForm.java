package com.jasper.munselfservice.controller.forms.course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseQueryForm {
    private Integer page;
    private Integer amount;
    private String courseNumber;
    private String name;
    private String instructorName;
}
