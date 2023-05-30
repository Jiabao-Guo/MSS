package com.jasper.munselfservice.v1.controller.forms.mcp;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
//@AllArgsConstructor
public class McpApplicationForm {
    private String maritalStatus;
    private Date applicationTime;
    private Boolean delivery;
    private String identityType;
    private String reason;
}
