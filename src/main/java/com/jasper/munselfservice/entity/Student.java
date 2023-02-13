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
@Table(name = "student", schema = "jasper")
public class Student {
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
    private Object gender;
    @Basic
    @Column(name = "age", nullable = false)
    private Integer age;
    @Basic
    @Column(name = "password_sha256_sha256", nullable = false, length = 64)
    private String passwordSha256Sha256;
    @OneToOne
    @JoinColumn(name = "student_number", referencedColumnName = "student_number", nullable = false, insertable = false, updatable = false)
    private McpApplication mcpApplicationByStudentNumber;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(studentNumber, student.studentNumber) && Objects.equals(studentName, student.studentName) && Objects.equals(gender, student.gender) && Objects.equals(age, student.age) && Objects.equals(passwordSha256Sha256, student.passwordSha256Sha256);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentNumber, studentName, gender, age, passwordSha256Sha256);
    }
}
