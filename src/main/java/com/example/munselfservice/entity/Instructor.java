package com.example.munselfservice.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Instructor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "instructor_number", nullable = false)
    private Integer instructorNumber;
    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Basic
    @Column(name = "salary", nullable = false, precision = 0)
    private Double salary;

    public Integer getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(Integer instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instructor that = (Instructor) o;
        return Objects.equals(instructorNumber, that.instructorNumber) && Objects.equals(name, that.name) && Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructorNumber, name, salary);
    }
}
