package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.entity.Student;
import com.jasper.munselfservice.helper.RedisHelper;
import com.jasper.munselfservice.model.Task;
import com.jasper.munselfservice.repository.*;
import com.jasper.munselfservice.service.TaskService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BaseController {
    @Resource
    protected RedisTemplate<Integer, String> redisTemplate;

    @Resource
    protected RedisTemplate<String, String> redisTemplateString;

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

    private TaskService taskService = null;

    protected TaskService getTaskService() {
        if (taskService == null) {
            taskService = new TaskService(new RedisHelper<>(redisTemplateString));
        }
        return taskService;
    }

    protected Student getStudentFromSessionId(Integer studentNumber, String sessionId) {
        return getStudentFromSessionId(redisTemplate, studentRepository, studentNumber, sessionId);
    }

    public static Student getStudentFromSessionId(RedisTemplate<Integer, String> redisTemplate, StudentRepository studentRepository, Integer studentNumber, String sessionId) {
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

    protected <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    protected ResponseEntity<GenericResponse> fail() {
        return ResponseEntity.ok(new GenericResponse(false, "Failed"));
    }

    protected ResponseEntity<GenericResponse> fail(String message) {
        return ResponseEntity.ok(new GenericResponse(false, message));
    }

    protected void handleUpload(Task task, MultipartFile file, Consumer<List<Map<String, String>>> consumer) {
        File target = new File(
            Path.of(
                System.getProperty("java.io.tmpdir"),
                file.getOriginalFilename()
            ).toAbsolutePath().toString()
        );

        try {
            file.transferTo(target);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Executors.newScheduledThreadPool(1).schedule(() -> {
            taskService.updateTask(task.getUuid(), "Reading csv...", 10, null);

            try {
                FileInputStream is = new FileInputStream(target);

                try (is; BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    Iterator<String> it = reader.lines().iterator();

                    if (!it.hasNext()) {
                        taskService.updateTask(task.getUuid(), "Error: Empty file.", 0, Task.STATUS_FAILED);
                        return;
                    }

                    String[] headers = it.next().strip().split(",");
                    List<Map<String, String>> entities = new ArrayList<>();

                    while (it.hasNext()) {
                        String line = it.next().strip();
                        if (line.isEmpty()) {
                            break;
                        }
                        String[] values = line.split(",");

                        Map<String, String> table = new HashMap<>();

                        for (int i = 0; i < headers.length; ++i) {
                            table.put(headers[i], values[i]);
                        }

                        entities.add(table);

                        if (entities.size() >= 100) {
                            consumer.accept(entities);
                            entities.clear();
                        }
                    }
                    if (entities.size() > 0) {
                        consumer.accept(entities);
                        entities.clear();
                    }
                    taskService.updateTask(task.getUuid(), "Done.", 100, Task.STATUS_SUCCESS);
                }
            } catch (Exception e) {
                taskService.updateTask(task.getUuid(), "Error: Malformed file.", 0, Task.STATUS_FAILED);
            }
        }, 2000, TimeUnit.MILLISECONDS);
    }
}
