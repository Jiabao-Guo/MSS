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

    @JoinColumn(name = "instructor_number", referencedColumnName = "instructor_number", nullable = false, insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Instructor instructorByInstructorNumber;
}
