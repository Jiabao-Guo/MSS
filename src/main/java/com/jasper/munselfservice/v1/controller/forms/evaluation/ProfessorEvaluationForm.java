package com.jasper.munselfservice.v1.controller.forms.evaluation;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProfessorEvaluationForm {
    private Integer instructorUid;
    private String region;
    private Date lectureStartDate;
    private Date lectureEndDate;
    private List<String> courseType;
    private String evaluation;
    private Integer satisfaction;
}
