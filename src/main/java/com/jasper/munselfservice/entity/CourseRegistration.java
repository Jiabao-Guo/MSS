package com.jasper.munselfservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
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

    @Basic
    @Column(name = "course_number", nullable = false)
    private Integer courseNumber;

    @ManyToOne
    @JoinColumn(name = "course_number", referencedColumnName = "course_number", nullable = false, insertable = false, updatable = false)
    private Course course;

    @Basic
    @Column(name = "course_registration_time", nullable = false)
    private Timestamp courseRegistrationTime;

    @Basic
    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;

    @ManyToOne
    @JoinColumn(name = "student_number", referencedColumnName = "student_number", nullable = false, insertable = false, updatable = false)
    private Student student;
}
