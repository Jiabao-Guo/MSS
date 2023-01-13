package com.example.munselfservice.controller;

import com.example.munselfservice.entity.Student;
import com.example.munselfservice.repository.*;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;

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

    /**
     *
     * 未来可能经常使用 从greetingcontroller 转移到这里
     *
     * 给定学号和SessionId，验证这个学号的SessionId是否正确
     * 如果正确，返回学生对象；否则返回null
     * @param studentNumber 学号
     * @param sessionId session id
     * @return Student or null
     *
     */
    protected Student validateStudentSession(Integer studentNumber, String sessionId) {
        // 得到这个学生的真实SessionID
        String realSessionId = redisTemplate
                .opsForValue()
                .get(studentNumber);

        // 与传来的SessionID比对
        if (Objects.equals(realSessionId, sessionId)) {
            // 如果正确，说明手环有效
            // 那就从数据库里取来完整学生信息
            return studentRepository.findByStudentNumber(studentNumber);
        }
        // 否则，返回null
        return null;
    }
}


