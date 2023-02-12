package com.example.munselfservice.controller;

import com.example.munselfservice.controller.forms.*;
import com.example.munselfservice.entity.Schedule;
import com.example.munselfservice.entity.Student;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class ScheduleController extends BaseController {
    @RequestMapping("/schedule/query")
    ResponseEntity<ScheduleResponse> querySchedule(@RequestBody GetScheduleForm form) {
        Student student = validateStudentSession(
                form.getStudentNumber(),
                form.getSessionId()
        );

        if (student == null) {
            return ResponseEntity.ok(new ScheduleResponse(
                    false, "Invalid session.", null
            ));
        }

        List<Schedule> schedules = scheduleRepository.findAllByStudentNumber(student.getStudentNumber());

        return ResponseEntity.ok(new ScheduleResponse(
                true, "success", schedules
        ));
    }

    @PostMapping("/schedule")
    ResponseEntity<GenericResponse> addSchedule(@RequestBody AddScheduleForm form) {
        Student student = validateStudentSession(
                form.getStudentNumber(),
                form.getSessionId()
        );

        if (student == null) {
            return ResponseEntity.ok(new GenericResponse(
                    false, "Invalid session."
            ));
        }

        Schedule schedule = new Schedule();
        schedule.setId(null);
        schedule.setStudentNumber(student.getStudentNumber());
        schedule.setDate(new Timestamp(form.getDate().getTime()));
        schedule.setSubject(form.getSubject());

        scheduleRepository.save(schedule);

        try {
            return ResponseEntity.ok(new GenericResponse(true, "Success"));
        }
        catch (Exception e) {
            return ResponseEntity.ok(new GenericResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/schedule/{scheduleId}")
    ResponseEntity<GenericResponse> updateSchedule(
            @PathVariable Integer scheduleId,
            @RequestBody Schedule givenSchedule // 从前段传来的 需要改成的instructor
    ) {
        try {
            Schedule target = scheduleRepository.getReferenceById(scheduleId);
            target.setDate(givenSchedule.getDate());
            target.setSubject(givenSchedule.getSubject());

            scheduleRepository.save(target);
            return ResponseEntity.ok(new GenericResponse(true, "Success"));
        }
        catch (Exception e) {
            return ResponseEntity.ok(new GenericResponse(false, e.getMessage()));
        }
    }

    @Transactional
    @DeleteMapping("/schedule/{scheduleIds}")
    ResponseEntity<GenericResponse> deleteSchedules(@PathVariable List<Integer> scheduleIds) {
        try {
            //instructorRepository数据库的封装
            scheduleRepository.deleteAllByIdIn(scheduleIds);
            return ResponseEntity.ok(new GenericResponse(true, "Success"));
        }
        catch (Exception e) {
            return ResponseEntity.ok(new GenericResponse(
                    false, "Delete failed: " + e.getMessage()));
        }
    }
}
