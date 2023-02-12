package com.example.munselfservice.repository;

import com.example.munselfservice.entity.Schedule;
import com.example.munselfservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findAllByStudentNumber(Integer studentNumber);

    void deleteAllByIdIn(List<Integer> ids);
}
