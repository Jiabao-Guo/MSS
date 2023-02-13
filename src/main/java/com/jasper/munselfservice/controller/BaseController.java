package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.entity.Student;
import com.jasper.munselfservice.repository.*;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class BaseController {
    @Resource
    protected RedisTemplate<Integer, String> redisTemplate;

    @Resource
    protected StudentRepository studentRepository;

    @Resource
    protected InstructorRepository instructorRepository;

    @Resource
    protected GradeRepository gradeRepository;

    @Resource
    protected CourseRepository courseRepository;

    @Resource
    protected ProfessorRatingRepository professorRatingRepository;

    @Resource
    protected McpApplicationRepository mcpApplicationRepository;

    @Resource
    protected ScheduleRepository scheduleRepository;

    @Resource
    protected CourseRegistrationRepository courseRegistrationRepository;


    protected Student getStudentFromSessionId(Integer studentNumber, String sessionId) {
        // TODO: 2023/2/12 临时测试用
        if (sessionId.equals("1")) {
            return studentRepository.findByStudentNumber(202096888);
        }

        String realSessionId = redisTemplate
                .opsForValue()
                .get(studentNumber);

        if (Objects.equals(realSessionId, sessionId)) {
            return studentRepository.findByStudentNumber(studentNumber);
        }
        return null;
    }

    protected ResponseEntity<GenericResponse> ok() {
        return ResponseEntity.ok(new GenericResponse(true, "Success"));
    }

    protected ResponseEntity<GenericResponse> ok(String message) {
        return ResponseEntity.ok(new GenericResponse(true, message));
    }

    protected ResponseEntity<GenericResponse> fail() {
        return ResponseEntity.ok(new GenericResponse(false, "Failed"));
    }

    protected ResponseEntity<GenericResponse> fail(String message) {
        return ResponseEntity.ok(new GenericResponse(false, message));
    }
}
