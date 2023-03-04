package com.jasper.munselfservice.v1.controller.user;

import com.jasper.munselfservice.util.NumericUtil;
import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.controller.forms.user.InstructorQueryForm;
import com.jasper.munselfservice.v1.controller.forms.user.StudentQueryForm;
import com.jasper.munselfservice.v1.controller.generic.AbstractRestfulController;
import com.jasper.munselfservice.v1.entity.CourseRegistration;
import com.jasper.munselfservice.v1.entity.StudentInfo;
import com.jasper.munselfservice.v1.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController extends AbstractUserController<StudentQueryForm> {
    @Override
    @PostMapping("/api/v1/student")
    public ResponseEntity<GenericResponse> create(@RequestBody User entity) {
        return super.create(entity);
    }

    @Override
    @PostMapping("/api/v1/student/upload")
    public ResponseEntity<GenericResponse> bulkCreate(@RequestParam("file") MultipartFile file) {
        return super.bulkCreate(file);
    }

    @Override
    @GetMapping("/api/v1/student")
    public Page<User> retrieve(StudentQueryForm form) {
        return userRepository.advancedFilterForStudent(
            PageRequest.of(form.getPage(), form.getAmount()),
            NumericUtil.parseIntOrDefault(form.getUid(), 0),
            form.getName(),
            form.getGender(),
            form.getEmail(),
            NumericUtil.parseIntOrDefault(form.getAgeLowerBound(), Integer.MIN_VALUE),
            NumericUtil.parseIntOrDefault(form.getAgeUpperBound(), Integer.MAX_VALUE)
        );
    }

    @Override
    @PutMapping("/api/v1/student/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody User givenEntity) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return fail("User not found");
        }
        User user = userOpt.get();
        user.setName(givenEntity.getName());
        user.setEmail(givenEntity.getEmail());
        user.getStudentInfo().setAge(givenEntity.getStudentInfo().getAge());
        user.getStudentInfo().setGender(givenEntity.getStudentInfo().getGender());

        try {
            userRepository.save(user);
            return ok();
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/api/v1/student/{ids}")
    @Transactional
    public ResponseEntity<GenericResponse> delete(@PathVariable List<Integer> ids) {
        return super.delete(ids);
    }
}
