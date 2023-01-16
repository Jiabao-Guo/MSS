package com.example.munselfservice.repository;

import com.example.munselfservice.entity.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
}