package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}