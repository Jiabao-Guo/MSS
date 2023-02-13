package com.jasper.munselfservice.controller;


import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.instructor.InstructorQueryForm;
import com.jasper.munselfservice.entity.Instructor;
import com.jasper.munselfservice.util.NumericUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstructorController extends BaseController {
    @GetMapping("/instructor/by-name/{name}")
    List<Instructor> findByName(@PathVariable String name) {
        return instructorRepository
            .findByNameContaining(PageRequest.of(0, 20), name)
            .getContent();
    }

    @PostMapping("/instructor")
    ResponseEntity<GenericResponse> create(@RequestBody Instructor instructor) {
        try {
            instructor.setPasswordSha256Sha256(null);
            instructorRepository.save(instructor);
        } catch (Exception e) {
            return fail();
        }

        return ok();
    }

    @GetMapping("/instructor")
    Page<Instructor> retrieve(InstructorQueryForm form) {
        Pageable pageable = PageRequest.of(form.getPage(), form.getAmount());

        Integer instructorNumber = NumericUtil.parseIntOrDefault(form.getInstructorNumber(), 0);
        Double min = NumericUtil.parseDoubleOrDefault(form.getMin(), -999999999.0);
        Double max = NumericUtil.parseDoubleOrDefault(form.getMax(), 999999999.0);

        return instructorRepository.findByInstructorNumberEqualsOrSalaryBetweenAndNameContaining(
            pageable, instructorNumber, min, max, form.getName()
        );
    }

    // DELETE/instructor/1,2,3,4,5
    @Transactional
    @DeleteMapping("/instructor/{ids}")
    ResponseEntity<GenericResponse> delete(@PathVariable List<Integer> ids) {
        try {
            instructorRepository.deleteAllByIdIn(ids);
        } catch (Throwable e) {
            if (e.getMessage().contains("foreign key constraint fails")) {
                return fail("They are used by other entities");
            }
            return fail();
        }

        return ok();
    }

    @PutMapping("/instructor/{id}")
    ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody Instructor givenInstructor) {
        try {
            Instructor targetInstructor = instructorRepository.getReferenceById(id);
            targetInstructor.setName(givenInstructor.getName());
            targetInstructor.setSalary(givenInstructor.getSalary());
            instructorRepository.save(targetInstructor);
        } catch (Exception e) {
            return fail();
        }

        return ok();
    }
}
