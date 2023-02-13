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
@Table(name = "grade", schema = "jasper")
public class Grade {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;
    @Basic
    @Column(name = "grade", nullable = false, precision = 0)
    private Double grade;
    @Basic
    @Column(name = "course_number", nullable = false)
    private Integer courseNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Objects.equals(id, grade1.id) && Objects.equals(studentNumber, grade1.studentNumber) && Objects.equals(grade, grade1.grade) && Objects.equals(courseNumber, grade1.courseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentNumber, grade, courseNumber);
    }
}
