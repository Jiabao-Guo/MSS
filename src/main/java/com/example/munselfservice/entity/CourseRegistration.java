package com.example.munselfservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "course_registration", schema = "jasper", catalog = "")
@Getter
@Setter
public class CourseRegistration {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;


    //数据库存入课程号 最后显示课程名字 多表查询
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_number", referencedColumnName = "course_number")
    private Course course;

    @Basic
    @Column(name = "course_registration_time", nullable = false)
    private Timestamp courseRegistrationTime;

    //多表查询
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_number", referencedColumnName = "student_number")
    private Student student;
}
