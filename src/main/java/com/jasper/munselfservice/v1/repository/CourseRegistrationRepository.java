package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
}