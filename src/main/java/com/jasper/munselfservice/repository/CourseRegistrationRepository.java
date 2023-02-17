package com.jasper.munselfservice.repository;

import com.jasper.munselfservice.entity.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
    Set<CourseRegistration> findAllByStudent_Id(Integer studentId);
}
