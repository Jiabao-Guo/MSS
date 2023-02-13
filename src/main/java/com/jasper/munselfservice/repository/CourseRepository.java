package com.jasper.munselfservice.repository;

import com.jasper.munselfservice.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Page<Course> findByCourseNumberEquals(Pageable pageable, Integer courseNumber);
    Page<Course> findByNameContainingAndInstructorByInstructorNumber_NameContaining(
        Pageable pageable,
        String name,
        String instructorName
    );
}
