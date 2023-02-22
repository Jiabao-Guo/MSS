package com.jasper.munselfservice.repository;

import com.jasper.munselfservice.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByStudentNumber(Integer studentNumber);

    Optional<Student> findById(Integer id);

    Page<Student> findByStudentNumberEqualsOrAgeBetweenOrGenderEqualsOrStudentNameContaining(
        Pageable pageable,
        Integer studentNumber,
        Integer ageMin,
        Integer ageMax,
        Student.Gender gender,
        String studentName
    );
}
