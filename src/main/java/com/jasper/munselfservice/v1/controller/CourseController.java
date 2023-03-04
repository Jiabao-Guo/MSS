package com.jasper.munselfservice.v1.controller;

import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.controller.forms.course.CourseQueryForm;
import com.jasper.munselfservice.v1.controller.generic.AbstractRestfulController;
import com.jasper.munselfservice.v1.entity.Course;
import com.jasper.munselfservice.v1.model.Task;
import com.jasper.munselfservice.util.NumericUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController extends AbstractRestfulController<Course, CourseQueryForm> {
    @Override
    @PostMapping("/api/v1/course")
    public ResponseEntity<GenericResponse> create(@RequestBody Course course) {
        try {
            courseRepository.save(course);
            return ok();
        } catch (Exception e) {
            return fail("Course number already exists");
        }
    }

    @Override
    @PostMapping("/api/v1/course/upload")
    public ResponseEntity<GenericResponse> bulkCreate(@RequestParam("file") MultipartFile file) {
        Task task = getTaskService().createTask("File Upload", "Reading csv...", 100);
        handleUploadingFile(task, file);
        return ok(task.getUuid());
    }

    @Override
    @GetMapping("/api/v1/course")
    public Page<Course> retrieve(CourseQueryForm form) {
        Integer courseNumber = NumericUtil.parseIntOrDefault(form.getCourseNumber(), 0);
        return courseRepository.advancedFilter(
            PageRequest.of(form.getPage(), form.getAmount()),
             courseNumber,
             form.getName(), form.getTaughtBy(), form.getMajorName()
        );
    }

    @Override
    @PutMapping("/api/v1/course/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody Course givenCourse) {
        Course course = courseRepository.getReferenceById(id);

        course.setName(givenCourse.getName());
        course.setUserId(givenCourse.getUserId());
        course.setCourseNumber(givenCourse.getCourseNumber());
        course.getUser().setName(givenCourse.getUser().getName());

        try {
            courseRepository.save(course);
        } catch (Exception e) {
            return fail();
        }
        return ok();
    }

    @Override
    @DeleteMapping("/api/v1/course/{ids}")
    @Transactional
    public ResponseEntity<GenericResponse> delete(@PathVariable List<Integer> ids) {
        courseRepository.deleteAllByIdIn(ids);
        return ok();
    }

    private void handleUploadingFile(Task task, MultipartFile file) {
        handleUpload(task, file, (list) ->
            courseRepository.saveAll(
                list.stream().map(
                    item -> Course.builder()
                        .userId(NumericUtil.parseIntOrDefault(item.get("userId"), 0))
                        .courseNumber(NumericUtil.parseIntOrDefault(item.get("courseNumber"), 0))
                        .name(item.get("name"))
                        .build()
                ).collect(Collectors.toSet())
            )
        );
    }
}
