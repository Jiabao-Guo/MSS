package com.example.munselfservice.controller;

import com.example.munselfservice.entity.McpApplication;
import com.example.munselfservice.entity.Student;
import com.example.munselfservice.controller.forms.McpApplicationForm;
import com.example.munselfservice.controller.forms.McpApplicationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class McpApplicationController extends BaseController{
    @RequestMapping("/mcp-application")
    public ResponseEntity<McpApplicationResponse>
    mcpApplication(@RequestBody McpApplicationForm form) {
        Student student = validateStudentSession(form.getStudentNumber(), form.getSessionId());

        if (student == null) {
            return ResponseEntity.ok(
                    new McpApplicationResponse(false,"Student not exists"));
        }

        McpApplication mcpApplication = new McpApplication(
                form.getStudentNumber(),
                form.getMaritalStatus(),
                new Timestamp(form.getApplicationTime().getTime()),
                (byte) (form.getDelivery() ? 1 : 0),
                form.getIdentityType(),
                form.getReason()
        );
        mcpApplicationRepository.save(mcpApplication);

        return ResponseEntity.ok(
                new McpApplicationResponse(true,"you have applicated MCP successfully"));
    }





}
