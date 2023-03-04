package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, Integer> {
}