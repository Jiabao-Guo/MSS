package com.jasper.munselfservice.v1.controller.forms.user;

import com.jasper.munselfservice.v1.entity.StudentInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentQueryForm {
    private Integer page;
    private Integer amount;
    private String uid;
    private String name;
    private String gender;
    private String email;
    private String ageLowerBound;
    private String ageUpperBound;
}
