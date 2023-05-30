package com.jasper.munselfservice.v1.controller.user;

import com.jasper.munselfservice.util.NumericUtil;
import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.controller.generic.AbstractRestfulController;
import com.jasper.munselfservice.v1.entity.InstructorInfo;
import com.jasper.munselfservice.v1.entity.StudentInfo;
import com.jasper.munselfservice.v1.entity.User;
import com.jasper.munselfservice.v1.model.Task;
import com.jasper.munselfservice.v1.service.MailerService;
import com.jasper.munselfservice.v1.util.PasswordUtil;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class AbstractUserController<QueryFormType> extends AbstractRestfulController<User, QueryFormType> {
    @Resource
    MailerService mailerService;

    @Override
    @PostMapping
    public ResponseEntity<GenericResponse> create(@RequestBody User entity) {
        try {
            String plaintext = UUID.randomUUID().toString();
            String password = PasswordUtil.createPassword(plaintext);
            entity.setPassword(password);
            entity.setBlocked((byte) 0);
            entity.setPrivileged((byte) 0);
            userRepository.save(entity);
            mailerService.sendPasswordEmail(entity.getEmail(), plaintext);
            Logger.getLogger("AbstractUserController").info("User created: " + entity.getEmail());
            return ok();
        } catch (Exception e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<GenericResponse> bulkCreate(@RequestParam("file") MultipartFile file) {
        Task task = getTaskService().createTask("File Upload", "Reading csv...", 100);
        handleUploadingFile(task, file);
        return ok(task.getUuid());
    }

    @Override
    @GetMapping
    abstract public Page<User> retrieve(QueryFormType form);

    @Override
    @PutMapping
    abstract public ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody User givenEntity);

    @Override
    @DeleteMapping
    @Transactional
    public ResponseEntity<GenericResponse> delete(@PathVariable List<Integer> ids) {
        userRepository.deleteAllByIdIn(ids);
        return ok();
    }

    private void handleUploadingFile(Task task, MultipartFile file) {
        Map<String, String> emailPasswordMap = new HashMap<>();

        ScheduledFuture<Boolean> future = handleUpload(task, file, (list) -> userRepository.saveAll(
            list.stream().map(
                item -> {
                    InstructorInfo instructorInfo = null;
                    StudentInfo studentInfo = null;

                    if (NumericUtil.parseBooleanOrDefault(item.get("isInstructor"), false)) {
                        instructorInfo = InstructorInfo.builder()
                            .salary(NumericUtil.parseDoubleOrDefault(item.get("salary"), 0.0))
                            .build();
                    }

                    if (NumericUtil.parseBooleanOrDefault(item.get("isStudent"), false)) {
                        studentInfo = StudentInfo.builder()
                            .gender(item.get("gender"))
                            .age(NumericUtil.parseIntOrDefault(item.get("age"), 0))
                            .build();
                    }

                    String password = PasswordUtil.createPassword(
                        UUID.randomUUID().toString());
                    String email = item.get("email");
                    emailPasswordMap.put(email, password);

                    return User.builder()
                        .uid(NumericUtil.parseIntOrDefault(item.get("uid"), 0))
                        .name(item.get("name"))
                        .email(email)
                        .password(password)
                        .blocked(NumericUtil.parseIntOrDefault(item.get("blocked"), 0).byteValue())
                        .privileged(NumericUtil.parseIntOrDefault(item.get("privileged"), 0).byteValue())
                        .instructorInfo(instructorInfo)
                        .studentInfo(studentInfo)
                        .build();
                }).collect(Collectors.toSet())
        ));

        Executors.newFixedThreadPool(1).submit(() -> {
            try {
                if (future.get()) {
                    for (Map.Entry<String, String> entry : emailPasswordMap.entrySet()) {
                        mailerService.sendPasswordEmail(entry.getKey(), entry.getValue());
                    }
                    Logger.getLogger("File Upload").info("Emails sent");
                }
            }
            catch (Exception ignored) {
            }
        });
    }

}
