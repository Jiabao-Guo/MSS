package com.example.munselfservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class ProfessorRatingPK implements Serializable {
    @Column(name = "student_number", nullable = false)
    @Id
 //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentNumber;
    @Column(name = "instructor_number", nullable = false)
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer instructorNumber;

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(Integer instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessorRatingPK that = (ProfessorRatingPK) o;
        return Objects.equals(studentNumber, that.studentNumber) && Objects.equals(instructorNumber, that.instructorNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, instructorNumber);
    }
}
