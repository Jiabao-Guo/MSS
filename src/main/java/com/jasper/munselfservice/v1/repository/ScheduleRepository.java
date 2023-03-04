package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findAllByUser_Uid(Integer uid);

    void deleteAllByIdIn(List<Integer> ids);
}
