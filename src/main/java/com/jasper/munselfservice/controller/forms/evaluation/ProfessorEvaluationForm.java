package com.jasper.munselfservice.controller.forms.evaluation;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProfessorEvaluationForm {
    private Integer studentNumber;
    private String sessionId;
    private Integer instructorNumber;
    private String region;
    private Date lectureStartDate;
    private Date lectureEndDate;
    private List<String> courseType;
    private String evaluation;
    private Double satisfaction;
}
