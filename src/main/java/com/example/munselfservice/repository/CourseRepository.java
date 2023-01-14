package com.example.munselfservice.repository;

import com.example.munselfservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {


}