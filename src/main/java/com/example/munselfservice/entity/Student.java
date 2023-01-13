package com.example.munselfservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity

@Getter
@Setter
public class Student {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;
    @Basic
    @Column(name = "student_name", nullable = false, length = 32)
    private String studentName;
    @Basic
    @Column(name = "gender", nullable = false)
    private Object gender;
    @Basic
    @Column(name = "age", nullable = false)
    private Integer age;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentNumber, student.studentNumber) && Objects.equals(studentName, student.studentName) && Objects.equals(gender, student.gender) && Objects.equals(age, student.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, studentName, gender, age);
    }
}
