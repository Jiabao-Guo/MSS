package com.jasper.munselfservice.v1.controller.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponse {
    Boolean success;
    String message;
}
