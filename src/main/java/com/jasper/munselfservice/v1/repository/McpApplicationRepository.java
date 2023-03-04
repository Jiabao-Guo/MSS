package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.McpApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McpApplicationRepository extends JpaRepository<McpApplication, Integer> {
}