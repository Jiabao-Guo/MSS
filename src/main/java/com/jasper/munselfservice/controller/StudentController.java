package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.student.StudentQueryForm;
import com.jasper.munselfservice.controller.generic.AbstractRestfulController;
import com.jasper.munselfservice.entity.CourseRegistration;
import com.jasper.munselfservice.entity.Student;
import com.jasper.munselfservice.model.Task;
import com.jasper.munselfservice.util.NumericUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class StudentController extends AbstractRestfulController<Student, StudentQueryForm> {
    @GetMapping("/student/{id}/course-registration")
    public Set<CourseRegistration> courseRegistration(@PathVariable Integer id) {
        try {
            return courseRegistrationRepository.findAllByStudent_Id(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @PostMapping("/student")
    public ResponseEntity<GenericResponse> create(@RequestBody Student entity) {
        try {
            entity.setPassword(null);
            studentRepository.save(entity);
        } catch (Exception e) {
            return fail();
        }
        return ok();
    }

    @Override
    @GetMapping("/student")
    public Page<Student> retrieve(StudentQueryForm form) {
        Pageable pageable = PageRequest.of(form.getPage(), form.getAmount());

        Student.Gender gender = form.getGender()
            .strip()
            .toLowerCase(Locale.ROOT)
            .equals("f")
            ? Student.Gender.f
            : Student.Gender.m;

        Integer studentNumber = NumericUtil.parseIntOrDefault(form.getStudentNumber(), 0);

        String studentName = StringUtils.hasText(form.getStudentName())
            ? form.getStudentName()
            : "";

        Integer ageMin = NumericUtil.parseIntOrDefault(form.getAgeMin(), 0);
        Integer ageMax = NumericUtil.parseIntOrDefault(form.getAgeMax(), 999999999);

        return studentRepository.findByStudentNumberEqualsOrAgeBetweenOrGenderEqualsOrStudentNameContaining(
            pageable,
            studentNumber,
            ageMin, ageMax,
            gender,
            studentName
        );
    }

    @Override
    @PutMapping("/student/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody Student givenEntity) {
        try {
            Student target = studentRepository.getReferenceById(id);

            target.setAge(givenEntity.getAge());
            target.setGender(givenEntity.getGender());
            target.setStudentName(givenEntity.getStudentName());

            studentRepository.save(target);
        } catch (Exception e) {
            return fail();
        }
        return ok();
    }

    @Override
    @DeleteMapping("/student/{ids}")
    @Transactional
    public ResponseEntity<GenericResponse> delete(@PathVariable List<Integer> ids) {
        studentRepository.deleteAllById(ids);
        return ok();
    }

    @Override
    @PostMapping("/student/upload")
    public ResponseEntity<GenericResponse> bulkCreate(@RequestParam("file") MultipartFile file) {
        Task task = getTaskService().createTask("File Upload", "Reading csv...", 100);
        handleUploadingFile(task, file);
        return ok(task.getUuid());
    }

    private void handleUploadingFile(Task task, MultipartFile file) {
        handleUpload(task, file, (list) ->
            studentRepository.saveAll(
                list.stream().map(item -> {
                    Student s = new Student();
                    s.setStudentNumber(NumericUtil.parseIntOrDefault(item.get("studentNumber"), 0));
                    s.setStudentName(item.get("studentName"));
                    s.setGender(item.get("gender").equals("f") ? Student.Gender.f : Student.Gender.m);
                    s.setAge(NumericUtil.parseIntOrDefault(item.get("age"), 0));
                    s.setPassword(null);
                    return s;
                }).collect(Collectors.toSet())
            )
        );
    }
}
