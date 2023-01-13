package com.example.munselfservice.object;


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
