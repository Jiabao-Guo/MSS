package com.jasper.munselfservice.repository;

import com.jasper.munselfservice.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Instructor findInstructorByInstructorNumber(Integer instructorNumber);

    Page<Instructor> findAll(Pageable pageable);

    Page<Instructor> findBySalaryBetweenAndNameContaining(
        Pageable pageable,
        Double min,
        Double max,
        String name
    );

    Page<Instructor> findByNameContaining(Pageable pageable, String name);

    void deleteAllByInstructorNumberIn(List<Integer> instructorNumbers);
}
