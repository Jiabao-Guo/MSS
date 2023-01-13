package com.example.munselfservice.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@IdClass(GradePK.class)
public class Grade {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;
    @Basic
    @Column(name = "grade", nullable = false, precision = 0)
    private Double grade;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "course_number", nullable = false)
    private Integer courseNumber;

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
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
        Grade grade1 = (Grade) o;
        return Objects.equals(studentNumber, grade1.studentNumber) && Objects.equals(grade, grade1.grade) && Objects.equals(courseNumber, grade1.courseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, grade, courseNumber);
    }
}
