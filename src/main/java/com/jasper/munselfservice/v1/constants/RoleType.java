package com.jasper.munselfservice.v1.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    STUDENT("STUDENT"),
    INSTRUCTOR("INSTRUCTOR"),
    ADMINISTRATOR("ADMINISTRATOR");

    private final String description;
}
