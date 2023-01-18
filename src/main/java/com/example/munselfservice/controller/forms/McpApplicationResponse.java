package com.example.munselfservice.controller.forms;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class McpApplicationResponse {
    Boolean isSuccess;

    String messages;
}
