package com.example.munselfservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class GradePK implements Serializable {
    @Column(name = "student_number", nullable = false)
    @Id
 //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentNumber;
    @Column(name = "course_number", nullable = false)
    @Id
 //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseNumber;

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradePK gradePK = (GradePK) o;
        return Objects.equals(studentNumber, gradePK.studentNumber) && Objects.equals(courseNumber, gradePK.courseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, courseNumber);
    }
}
