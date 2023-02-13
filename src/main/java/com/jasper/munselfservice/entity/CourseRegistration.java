package com.jasper.munselfservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_registration", schema = "jasper")
public class CourseRegistration {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "course_number", referencedColumnName = "course_number", nullable = false)
    private Course course;

    @Basic
    @Column(name = "course_registration_time", nullable = false)
    private Timestamp courseRegistrationTime;

    @ManyToOne
    @JoinColumn(name = "student_number", referencedColumnName = "student_number", nullable = false)
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRegistration that = (CourseRegistration) o;
        return Objects.equals(id, that.id) && Objects.equals(course, that.course) && Objects.equals(courseRegistrationTime, that.courseRegistrationTime) && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course.getCourseNumber(), courseRegistrationTime, student.getStudentNumber());
    }
}
