package com.jasper.munselfservice.v1.controller.generic;

import com.jasper.munselfservice.v1.constants.RoleType;
import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.entity.Role;
import com.jasper.munselfservice.v1.helper.RedisHelper;
import com.jasper.munselfservice.v1.model.Task;
import com.jasper.munselfservice.v1.repository.*;
import com.jasper.munselfservice.v1.service.TaskService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BaseController {
    @Resource
    protected RedisTemplate<Integer, String> redisTemplate;

    @Resource
    protected RedisTemplate<String, String> redisTemplateString;

    @Resource
    protected StudentInfoRepository studentInfoRepository;

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

    @Resource
    protected RoleRepository roleRepository;

    @Resource
    protected UserRepository userRepository;

    @Resource
    JwtEncoder jwtEncoder;

    @Resource
    JwtDecoder jwtDecoder;

    public Integer getUserUid(Jwt token) {
        return Integer.parseInt(token.getClaimAsString("userUid"));
    }

    public Integer getUserId(Jwt token) {
        return Integer.parseInt(token.getClaimAsString("userId"));
    }

    public String getName(Jwt token) {
        return token.getClaim("name");
    }

    public Boolean isPrivileged(Jwt token) {
        return Boolean.parseBoolean(token.getClaimAsString("privileged"));
    }

    public Boolean hasRole(Jwt token, RoleType role) {
        return Arrays.stream(token.getClaimAsString("roles").split(","))
            .anyMatch(r -> r.equals(role.name()));
    }

    public List<String> getRoles(Jwt token) {
        return Arrays.stream(token.getClaimAsString("roles").split(","))
            .toList();
    }

    public Boolean getPrivileged(Jwt token) {
        return Boolean.parseBoolean(token.getClaimAsString("privileged"));
    }

    public Optional<Jwt> parseJwt(String token) {
        try {
            return Optional.of(jwtDecoder.decode(token));
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    public Jwt createJwtToken(
        String subject,
        String name,
        Integer userId,
        Integer userUid,
        Boolean privileged,
        Collection<Role> roles
    ) {
        Instant now = Instant.now();
        Instant expireAt = now.plusSeconds(3600);

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("龙傲天")
            .issuedAt(now)
            .expiresAt(expireAt)
            .subject(subject)
            .claim("name", name)
            .claim("userUid", userUid.toString())
            .claim("userId", userId.toString())
            .claim("privileged", privileged.toString())
            .claim("roles", roles.stream().map(Role::getName).collect(Collectors.joining(",")))
            .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }

    private TaskService taskService = null;

    protected TaskService getTaskService() {
        if (taskService == null) {
            taskService = new TaskService(new RedisHelper<>(redisTemplateString));
        }
        return taskService;
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


    public ScheduledFuture<Boolean> handleUpload(Task task, MultipartFile file, Consumer<List<Map<String, String>>> consumer) {
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

        return Executors.newScheduledThreadPool(1).schedule(() -> {
            taskService.updateTask(task.getUuid(), "Reading csv...", 10, null);

            try {
                FileInputStream is = new FileInputStream(target);

                try (is; BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    Iterator<String> it = reader.lines().iterator();

                    if (!it.hasNext()) {
                        taskService.updateTask(task.getUuid(), "Error: Empty file.", 0, Task.STATUS_FAILED);
                        return false;
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
                    return true;
                }
            } catch (Exception e) {
                taskService.updateTask(task.getUuid(), "Error: Malformed file.", 0, Task.STATUS_FAILED);
                return false;
            }
        }, 2000, TimeUnit.MILLISECONDS);
    }
}
