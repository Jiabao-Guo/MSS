package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.schedule.AddScheduleForm;
import com.jasper.munselfservice.controller.forms.schedule.GetScheduleForm;
import com.jasper.munselfservice.controller.forms.schedule.ScheduleResponse;
import com.jasper.munselfservice.entity.Schedule;
import com.jasper.munselfservice.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class ScheduleController extends BaseController {
    @RequestMapping("/schedule/query")
    ResponseEntity<ScheduleResponse> querySchedule(@RequestBody GetScheduleForm form) {
        Student student = getStudentFromSessionId(
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
        Student student = getStudentFromSessionId(
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
            return ok();
        } catch (Exception e) {
            return fail();
        }
    }

    @PutMapping("/schedule/{scheduleId}")
    ResponseEntity<GenericResponse> updateSchedule(
        @PathVariable Integer scheduleId,
        @RequestBody Schedule givenSchedule
    ) {
        try {
            Schedule target = scheduleRepository.getReferenceById(scheduleId);
            target.setDate(givenSchedule.getDate());
            target.setSubject(givenSchedule.getSubject());

            scheduleRepository.save(target);
            return ok();
        } catch (Exception e) {
            return fail();
        }
    }

    @Transactional
    @DeleteMapping("/schedule/{scheduleIds}")
    ResponseEntity<GenericResponse> deleteSchedules(@PathVariable List<Integer> scheduleIds) {
        try {
            //instructorRepository数据库的封装
            scheduleRepository.deleteAllByIdIn(scheduleIds);
            return ok();
        } catch (Exception e) {
            return fail();
        }
    }
}
