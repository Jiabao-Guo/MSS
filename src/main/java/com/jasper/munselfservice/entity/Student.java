package com.jasper.munselfservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student", schema = "jasper")
public class Student {
    public enum Gender {
        f, m
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;
    @Basic
    @Column(name = "student_name", nullable = false, length = 32)
    private String studentName;
    @Basic
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Basic
    @Column(name = "age", nullable = false)
    private Integer age;
    @Basic
    @Column(name = "password", nullable = true, length = 128)
    private String password;
}
