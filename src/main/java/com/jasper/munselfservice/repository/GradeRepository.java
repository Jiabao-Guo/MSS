package com.jasper.munselfservice.repository;

import com.jasper.munselfservice.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}