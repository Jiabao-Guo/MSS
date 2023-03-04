package com.jasper.munselfservice.v1.controller.forms.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddScheduleForm {
    Date date;
    String subject;
}
