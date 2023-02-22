package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.task.TaskQueryForm;
import com.jasper.munselfservice.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class TaskController extends BaseController {
    @GetMapping("/task")
    public ResponseEntity<List<Task>> retrieve(TaskQueryForm form) {
        return ok(
            new ArrayList<>(
                Arrays.stream(form.getUuids().split(","))
                    .map(uuid -> getTaskService().getTask(uuid))
                    .toList()
            )
        );
    }

    @DeleteMapping("/task/{uuid}")
    public ResponseEntity<GenericResponse> delete(@PathVariable String uuid) {
        return getTaskService().deleteTask(uuid)
            ? ok()
            : fail("Task does not exist");
    }
}
