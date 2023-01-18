package com.example.munselfservice.controller.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorQueryForm {
    private Integer page;
    private Integer amount;
    private Double min;
    private Double max;
    private String name;
}
