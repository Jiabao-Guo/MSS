package com.jasper.munselfservice.v1.controller;

import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.controller.forms.mcp.McpApplicationForm;
import com.jasper.munselfservice.v1.controller.generic.BaseController;
import com.jasper.munselfservice.v1.entity.McpApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class McpApplicationController extends BaseController {
    @RequestMapping("/api/v1/mcp-application")
    public ResponseEntity<GenericResponse> mcpApplication(@AuthenticationPrincipal Jwt token, @RequestBody McpApplicationForm form) {
        McpApplication application = McpApplication.builder()
            .userId(getUserId(token))
            .maritalStatus(form.getMaritalStatus())
            .applicationTime(new Timestamp(form.getApplicationTime().getTime()))
            .delivery((byte) (form.getDelivery() ? 1 : 0))
            .identityType(form.getIdentityType())
            .reason(form.getReason())
            .build();
        try {
            mcpApplicationRepository.save(application);
        }
        catch (Exception e) {
            return fail("Already applied");
        }

        return ok();
    }
}
