package com.jasper.munselfservice.repository;

import com.jasper.munselfservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByStudentNumber(Integer studentNumber);
}
