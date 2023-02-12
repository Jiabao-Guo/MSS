package com.example.munselfservice.controller.forms;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddScheduleForm {
    String sessionId;
    Integer studentNumber;
    Date date;
    String subject;
}
