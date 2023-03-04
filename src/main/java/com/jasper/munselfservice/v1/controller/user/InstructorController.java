package com.jasper.munselfservice.v1.controller.user;

import com.jasper.munselfservice.util.NumericUtil;
import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.controller.forms.user.InstructorQueryForm;
import com.jasper.munselfservice.v1.controller.generic.AbstractRestfulController;
import com.jasper.munselfservice.v1.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class InstructorController extends AbstractUserController<InstructorQueryForm> {
    @Override
    @PostMapping("/api/v1/instructor")
    public ResponseEntity<GenericResponse> create(@RequestBody User entity) {
        return super.create(entity);
    }

    @Override
    @PostMapping("/api/v1/instructor/upload")
    public ResponseEntity<GenericResponse> bulkCreate(@RequestParam("file") MultipartFile file) {
        return super.bulkCreate(file);
    }

    @Override
    @GetMapping("/api/v1/instructor")
    public Page<User> retrieve(InstructorQueryForm form) {
        return userRepository.advancedFilterForInstructor(
            PageRequest.of(form.getPage(), form.getAmount()),
            NumericUtil.parseIntOrDefault(form.getUid(), 0),
            form.getName(),
            form.getEmail(),
            NumericUtil.parseIntOrDefault(form.getSalaryLowerBound(), -Integer.MAX_VALUE),
            NumericUtil.parseIntOrDefault(form.getSalaryUpperBound(), Integer.MAX_VALUE)
        );
    }

    @Override
    @PutMapping("/api/v1/instructor/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody User givenEntity) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return fail("User not found");
        }
        User user = userOpt.get();
        user.setName(givenEntity.getName());
        user.setEmail(givenEntity.getEmail());
        user.getInstructorInfo().setSalary(givenEntity.getInstructorInfo().getSalary());

        try {
            userRepository.save(user);
            return ok();
        } catch (Exception e) {
            return fail("User already exists");
        }
    }

    @Override
    @DeleteMapping("/api/v1/instructor/{ids}")
    @Transactional
    public ResponseEntity<GenericResponse> delete(@PathVariable List<Integer> ids) {
        return super.delete(ids);
    }

    @GetMapping("/api/v1/instructor/by-name/{name}")
    public List<User> retrieveByName(@PathVariable String name) {
        return userRepository.findAllByNameContainingIgnoreCaseAndInstructorInfoIsNotNull(
            PageRequest.of(0, 20),
            name
        ).toList();
    }
}
