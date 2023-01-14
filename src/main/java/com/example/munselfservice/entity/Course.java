package com.example.munselfservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@IdClass(CoursePK.class)
@Getter
@Setter
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "course_number", nullable = false)
    private Integer courseNumber;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "name", nullable = false, length = 64)
    private String name;


    //数据库存入教授号码 前段还是得显示名字 多表查询
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "instructor_number",
            referencedColumnName = "instructor_number"
    )
    private Instructor instructor;
}
