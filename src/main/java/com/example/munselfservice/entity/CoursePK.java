package com.example.munselfservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class CoursePK implements Serializable {
    @Column(name = "course_number", nullable = false)
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseNumber;
    @Column(name = "name", nullable = false, length = 64)
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursePK coursePK = (CoursePK) o;
        return Objects.equals(courseNumber, coursePK.courseNumber) && Objects.equals(name, coursePK.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseNumber, name);
    }
}
