package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByCourseNumberIn(List<Integer> courseNumbers);

    @Query("SELECT c FROM Course c WHERE c.courseNumber = ?1 OR (c.name ILIKE %?2% AND c.user.name ILIKE %?3% AND c.major.name ILIKE %?4%)")
    Page<Course> advancedFilter(Pageable pageable, Integer courseNumber, String name, String taughtBy, String majorName);

    void deleteAllByIdIn(List<Integer> ids);
}
