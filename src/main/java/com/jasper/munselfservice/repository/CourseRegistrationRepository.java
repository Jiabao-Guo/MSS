package com.jasper.munselfservice.repository;

import com.jasper.munselfservice.entity.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
}