package com.example.munselfservice.repository;

import com.example.munselfservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByStudentNumber(Integer studentNumber);

}
