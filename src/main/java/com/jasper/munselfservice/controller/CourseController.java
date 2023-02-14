package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.course.CourseQueryForm;
import com.jasper.munselfservice.entity.Course;
import com.jasper.munselfservice.util.NumericUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class CourseController extends BaseController {
    @PostMapping("/course")
    public ResponseEntity<GenericResponse> create(@RequestBody Course course) {
        try {
            courseRepository.save(course);
        } catch (Exception e) {
            return fail("Course number already exists");
        }
        return ok();
    }

    @GetMapping("/course")
    public Page<Course> retrieve(CourseQueryForm form) {
        Integer courseNumber = NumericUtil.parseIntOrDefault(form.getCourseNumber(), 0);

        Page<Course> coursesByCourseNumber = courseRepository.findByCourseNumberEquals(
            PageRequest.of(form.getPage(), form.getAmount()),
            courseNumber
        );

        if (coursesByCourseNumber.getTotalElements() > 0) {
            return coursesByCourseNumber;
        }

        return courseRepository.findByNameContainingAndInstructorByInstructorNumber_NameContaining(
            PageRequest.of(form.getPage(), form.getAmount()),
            form.getName(),
            form.getInstructorName()
        );
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody Course givenCourse) {
        try {
            Course targetCourse = courseRepository.getReferenceById(id);
            targetCourse.setName(givenCourse.getName());
            targetCourse.setInstructorNumber(givenCourse.getInstructorNumber());
            targetCourse.setCourseNumber(givenCourse.getCourseNumber());
            courseRepository.save(targetCourse);
        } catch (Exception e) {
            return fail();
        }
        return ok();
    }

    @DeleteMapping("/course/{ids}")
    public ResponseEntity<GenericResponse> delete(@PathVariable List<Integer> ids) {
        courseRepository.deleteAllByIdIn(ids);
        return ok();
    }
}
