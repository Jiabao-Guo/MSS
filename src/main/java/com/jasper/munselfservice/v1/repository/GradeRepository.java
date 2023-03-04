package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}