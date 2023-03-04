package com.jasper.munselfservice.v1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "grade", schema = "jasper")
@Getter
@Setter
@Entity
public class Grade {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Basic
    @Column(name = "grade", nullable = false, precision = 0)
    private Double grade;
    @Basic
    @Column(name = "course_id", nullable = false)
    private Integer courseId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Course course;
}
