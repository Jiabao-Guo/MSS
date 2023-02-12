package com.example.munselfservice.controller.forms;

import com.example.munselfservice.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleResponse {
    Boolean isSuccess;
    String messages;
    List<Schedule> schedules;
}
