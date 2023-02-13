package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.mcp.McpApplicationForm;
import com.jasper.munselfservice.entity.McpApplication;
import com.jasper.munselfservice.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class McpApplicationController extends BaseController {
    @RequestMapping("/mcp-application")
    public ResponseEntity<GenericResponse> mcpApplication(@RequestBody McpApplicationForm form) {
        Student student = getStudentFromSessionId(form.getStudentNumber(), form.getSessionId());

        if (student == null) {
            return fail("Student does not exist");
        }

        McpApplication mcpApplication = new McpApplication(
            null,
            form.getStudentNumber(),
            form.getMaritalStatus(),
            new Timestamp(form.getApplicationTime().getTime()),
            (byte) (form.getDelivery() ? 1 : 0),
            form.getIdentityType(),
            form.getReason()
        );
        mcpApplicationRepository.save(mcpApplication);

        return ok();
    }
}
