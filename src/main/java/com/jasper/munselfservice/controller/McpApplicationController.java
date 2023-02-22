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

        try {
            McpApplication mcpApplication = new McpApplication();
            mcpApplication.setStudentNumber(student.getStudentNumber());
            mcpApplication.setMaritalStatus(form.getMaritalStatus());
            mcpApplication.setApplicationTime(new Timestamp(form.getApplicationTime().getTime()));
            mcpApplication.setDelivery((byte) (form.getDelivery() ? 1 : 0));
            mcpApplication.setIdentityType(form.getIdentityType());
            mcpApplication.setReason(form.getReason());

            mcpApplicationRepository.save(mcpApplication);
        }
        catch (Exception e) {
            return fail("Already applied");
        }

        return ok();
    }
}
