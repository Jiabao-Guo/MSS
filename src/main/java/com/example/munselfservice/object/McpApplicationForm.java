package com.example.munselfservice.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
//@AllArgsConstructor
public class McpApplicationForm {
    private Integer studentNumber;

    private String sessionId;
    private String maritalStatus;

    private Date applicationTime;

    private Boolean delivery;

    private String identityType;

    // 分数 0～5
    private String reason;
}
