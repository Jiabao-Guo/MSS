package com.jasper.munselfservice.controller.forms.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentQueryForm {
    private Integer page;
    private Integer amount;
    private String studentNumber;
    private String studentName;
    private String gender;
    private String ageMin;
    private String ageMax;
}
