package com.jasper.munselfservice.repository;

import com.jasper.munselfservice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findAllByStudentNumber(Integer studentNumber);

    void deleteAllByIdIn(List<Integer> ids);
}
