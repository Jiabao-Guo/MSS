package com.jasper.munselfservice.controller;


import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.instructor.InstructorQueryForm;
import com.jasper.munselfservice.entity.Instructor;
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

    @PutMapping("/instructor/{instructorNumber}")
    ResponseEntity<GenericResponse> modifyInstructor(
        @PathVariable Integer instructorNumber,
        @RequestBody Instructor givenInstructor
    ) {
        try {
            Instructor targetInstructor = instructorRepository
                .findInstructorByInstructorNumber(instructorNumber);
            targetInstructor.setName(givenInstructor.getName());
            targetInstructor.setSalary(givenInstructor.getSalary());
            instructorRepository.save(targetInstructor);
            return ok();
        } catch (Exception e) {
            return fail();
        }
    }

    // DELETE/instructor/1,2,3,4,5
    @Transactional
    @DeleteMapping("/instructor/{instructorNumbers}")
    ResponseEntity<GenericResponse> deleteInstructors(
        @PathVariable List<Integer> instructorNumbers
    ) {
        try {
            instructorRepository.deleteAllByInstructorNumberIn(instructorNumbers);
        } catch (Exception e) {
            return fail();
        }

        return ok();
    }

    @GetMapping("/instructor")
    Page<Instructor> findBySalaryBetweenAndNameContaining(InstructorQueryForm form) {
        Pageable pageable = PageRequest.of(form.getPage(), form.getAmount());
        return instructorRepository.findBySalaryBetweenAndNameContaining(
            pageable,
            form.getMin(),
            form.getMax(),
            form.getName()
        );
    }
}
