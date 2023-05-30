package com.jasper.munselfservice.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Table(name = "user", schema = "jasper")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "uid", nullable = false)
    private Integer uid;

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Basic
    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Basic
    @Column(name = "password", nullable = true, length = 128)
    @JsonIgnore
    private String password;

    @Basic
    @Column(name = "blocked", nullable = false)
    private Byte blocked;

    @Basic
    @Column(name = "privileged", nullable = false)
    private Byte privileged;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<Grade> grades;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<McpApplication> mcpApplications;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<Schedule> schedules;

    @OneToMany(mappedBy = "user")
    private Collection<UserRole> roles;

    @OneToOne(mappedBy = "user")
    private InstructorInfo instructorInfo;

    @OneToOne(mappedBy = "user")
    private StudentInfo studentInfo;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<CourseRegistration> courseRegistrations;
}
