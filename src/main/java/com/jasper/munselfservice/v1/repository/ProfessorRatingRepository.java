package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.ProfessorRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRatingRepository extends JpaRepository<ProfessorRating, Integer> {
    Boolean existsByCreator_Uid(Integer uid);
}
