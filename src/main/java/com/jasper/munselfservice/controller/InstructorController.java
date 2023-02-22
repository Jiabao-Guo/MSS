package com.jasper.munselfservice.controller;


import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.instructor.InstructorQueryForm;
import com.jasper.munselfservice.controller.generic.AbstractRestfulController;
import com.jasper.munselfservice.entity.Instructor;
import com.jasper.munselfservice.model.Task;
import com.jasper.munselfservice.util.NumericUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InstructorController extends AbstractRestfulController<Instructor, InstructorQueryForm> {
    @GetMapping("/instructor/by-name/{name}")
    public List<Instructor> findByName(@PathVariable String name) {
        return instructorRepository
            .findByNameContaining(PageRequest.of(0, 20), name)
            .getContent();
    }

    @Override
    @PostMapping("/instructor")
    public ResponseEntity<GenericResponse> create(@RequestBody Instructor instructor) {
        try {
            instructor.setPasswordSha256Sha256(null);
            instructorRepository.save(instructor);
        } catch (Exception e) {
            return fail();
        }

        return ok();
    }

    @Override
    @GetMapping("/instructor")
    public Page<Instructor> retrieve(InstructorQueryForm form) {
        Pageable pageable = PageRequest.of(form.getPage(), form.getAmount());

        Integer instructorNumber = NumericUtil.parseIntOrDefault(form.getInstructorNumber(), 0);
        Double min = NumericUtil.parseDoubleOrDefault(form.getMin(), -999999999.0);
        Double max = NumericUtil.parseDoubleOrDefault(form.getMax(), 999999999.0);

        return instructorRepository.findByInstructorNumberEqualsOrSalaryBetweenAndNameContaining(
            pageable, instructorNumber, min, max, form.getName()
        );
    }

    @Override
    @PutMapping("/instructor/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody Instructor givenInstructor) {
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

    // DELETE/instructor/1,2,3,4,5
    @Override
    @DeleteMapping("/instructor/{ids}")
    @Transactional
    public ResponseEntity<GenericResponse> delete(@PathVariable List<Integer> ids) {
        instructorRepository.deleteAllByIdIn(ids);
        return ok();
    }

    @Override
    @PostMapping("/instructor/upload")
    public ResponseEntity<GenericResponse> bulkCreate(@RequestParam("file") MultipartFile file) {
        Task task = getTaskService().createTask("File Upload", "Reading csv...", 100);
        handleUploadingFile(task, file);
        return ok(task.getUuid());
    }

    private void handleUploadingFile(Task task, MultipartFile file) {
        handleUpload(task, file, (list) ->
            instructorRepository.saveAll(
                list.stream().map(item -> {
                    Instructor s = new Instructor();
                    s.setInstructorNumber(NumericUtil.parseIntOrDefault(item.get("instructorNumber"), 0));
                    s.setName(item.get("name"));
                    s.setSalary(NumericUtil.parseDoubleOrDefault(item.get("salary"), 0.0));
                    s.setPasswordSha256Sha256(null);
                    return s;
                }).collect(Collectors.toSet())
            )
        );
    }
}
