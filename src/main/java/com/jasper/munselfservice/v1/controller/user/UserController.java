package com.jasper.munselfservice.v1.controller.user;

import com.jasper.munselfservice.util.NumericUtil;
import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.controller.forms.user.UserQueryForm;
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
public class UserController extends AbstractUserController<UserQueryForm> {
    @Override
    @PostMapping("/api/v1/user")
    public ResponseEntity<GenericResponse> create(@RequestBody User entity) {
        return super.create(entity);
    }

    @Override
    @PostMapping("/api/v1/user/upload")
    public ResponseEntity<GenericResponse> bulkCreate(@RequestParam("file") MultipartFile file) {
        return super.bulkCreate(file);
    }

    @Override
    @GetMapping("/api/v1/user")
    public Page<User> retrieve(UserQueryForm form) {
        return userRepository.advancedFilterForUser(
            PageRequest.of(form.getPage(), form.getAmount()),
            NumericUtil.parseIntOrDefault(form.getUid(), 0),
            form.getName(),
            form.getEmail(),
            NumericUtil.parseIntBooleanOrDefault(form.getBlocked(), 0),
            NumericUtil.parseIntBooleanOrDefault(form.getPrivileged(), 0)
        );
    }

    @Override
    @PutMapping("/api/v1/user/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody User givenEntity) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return fail("User not found");
        }
        User user = userOpt.get();
        user.setName(givenEntity.getName());
        user.setPrivileged(givenEntity.getPrivileged());
        user.setBlocked(givenEntity.getBlocked());
        user.setRoles(givenEntity.getRoles());

        try {
            userRepository.save(user);
            return ok();
        } catch (Exception e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @Override
    @DeleteMapping
    @Transactional
    public ResponseEntity<GenericResponse> delete(@PathVariable List<Integer> ids) {
        return super.delete(ids);
    }
}
