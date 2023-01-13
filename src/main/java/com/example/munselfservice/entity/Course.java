package com.example.munselfservice.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@IdClass(CoursePK.class)
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "course_number", nullable = false)
    private Integer courseNumber;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Basic
    @Column(name = "instructor_number", nullable = false)
    private Integer instructorNumber;

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
        Course course = (Course) o;
        return Objects.equals(courseNumber, course.courseNumber) && Objects.equals(name, course.name) && Objects.equals(instructorNumber, course.instructorNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseNumber, name, instructorNumber);
    }
}
