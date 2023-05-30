package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}