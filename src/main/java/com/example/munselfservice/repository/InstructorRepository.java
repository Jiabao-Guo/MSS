package com.example.munselfservice.repository;

import com.example.munselfservice.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    // List<Instructor> findByNameContaining(String name);

    //Pageable  本页的所有信息
    Page<Instructor> findAll(Pageable pageable);

    // 根据薪水筛查
    //满足用户特殊癖好 薪水加名字联合搜查
    Page<Instructor> findBySalaryBetweenAndNameContaining(
            Pageable pageable,
            Double min,
            Double max,
            String name
    );

    Page<Instructor> findByNameContaining(Pageable pageable, String name);
}
