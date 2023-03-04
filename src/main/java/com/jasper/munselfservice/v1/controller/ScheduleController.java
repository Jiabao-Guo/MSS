package com.jasper.munselfservice.v1.controller;

import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.controller.forms.schedule.AddScheduleForm;
import com.jasper.munselfservice.v1.controller.forms.schedule.ScheduleResponse;
import com.jasper.munselfservice.v1.controller.generic.BaseController;
import com.jasper.munselfservice.v1.entity.Schedule;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class ScheduleController extends BaseController {
    @GetMapping("/api/v1/schedule")
    ResponseEntity<ScheduleResponse> querySchedule(@AuthenticationPrincipal Jwt token) {
        Integer userUid = getUserUid(token);
        List<Schedule> schedules = scheduleRepository.findAllByUser_Uid(userUid);
        return ok(new ScheduleResponse(true, "success", schedules));
    }

    @PostMapping("/api/v1/schedule")
    ResponseEntity<GenericResponse> addSchedule(@AuthenticationPrincipal Jwt token, @RequestBody AddScheduleForm form) {
        Schedule schedule = Schedule.builder()
            .userId(getUserId(token))
            .date(new Timestamp(form.getDate().getTime()))
            .subject(form.getSubject())
            .build();

        try {
            scheduleRepository.save(schedule);
            return ok();
        } catch (Exception e) {
            return fail();
        }
    }

    @PutMapping("/api/v1/schedule/{scheduleId}")
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
    @DeleteMapping("/api/v1/schedule/{scheduleIds}")
    ResponseEntity<GenericResponse> deleteSchedules(@PathVariable List<Integer> scheduleIds) {
        try {
            scheduleRepository.deleteAllByIdIn(scheduleIds);
            return ok();
        } catch (Exception e) {
            return fail();
        }
    }
}
