package com.jasper.munselfservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course", schema = "jasper")
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "course_number", nullable = false)
    private Integer courseNumber;
    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Basic
    @Column(name = "instructor_number", nullable = false)
    private Integer instructorNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(courseNumber, course.courseNumber) && Objects.equals(name, course.name) && Objects.equals(instructorNumber, course.instructorNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseNumber, name, instructorNumber);
    }
}
