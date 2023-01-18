package com.example.munselfservice.controller.forms;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProfessorEvaluationForm {

    //根据前段
    // onSubmit() {
    //          axios.post('http://localhost:8080/professor-evaluation', {
    //            ...this.form,  [包含很多属性]
    //            studentNumber: localStorage.getItem("student_number"),
    //            sessionId: localStorage.getItem("session"),
    //          })
    private Integer studentNumber;
    private String sessionId;
    private Integer instructorNumber;
    private String region;
    private Date lectureStartDate;
    private Date lectureEndDate;

    // 课程类别
    private List<String> courseType;

    // 评价的内容
    private String evaluation;

    // 分数 0～5
    private Double satisfaction;
}


