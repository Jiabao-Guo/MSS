package com.jasper.munselfservice.v1.controller.forms.schedule;

import com.jasper.munselfservice.v1.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleResponse {
    Boolean success;
    String message;
    List<Schedule> schedules;
}
