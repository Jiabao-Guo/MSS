package com.jasper.munselfservice.repository;

import com.jasper.munselfservice.entity.ProfessorRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRatingRepository extends JpaRepository<ProfessorRating, Integer> {

    Boolean existsProfessorRatingByStudentNumberAndInstructorNumber(Integer studentNumber, Integer instructorNumber);
}
