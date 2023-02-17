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
@Table(name = "instructor", schema = "jasper")
public class Instructor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "instructor_number", nullable = false)
    private Integer instructorNumber;
    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Basic
    @Column(name = "salary", nullable = false, precision = 0)
    private Double salary;
    @Basic
    @Column(name = "password_sha256_sha256", nullable = true, length = 64)
    private String passwordSha256Sha256;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instructor that = (Instructor) o;
        return Objects.equals(id, that.id) && Objects.equals(instructorNumber, that.instructorNumber) && Objects.equals(name, that.name) && Objects.equals(salary, that.salary) && Objects.equals(passwordSha256Sha256, that.passwordSha256Sha256);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instructorNumber, name, salary, passwordSha256Sha256);
    }
}
