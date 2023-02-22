package com.jasper.munselfservice.controller.generic;

import com.jasper.munselfservice.controller.BaseController;
import com.jasper.munselfservice.controller.forms.GenericResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract class AbstractRestfulController<EntityType, EntityQueryFormType>
    extends BaseController {
    @PostMapping
    public abstract ResponseEntity<GenericResponse> create(
        @RequestBody EntityType entity
    );

    @PostMapping
    public abstract ResponseEntity<GenericResponse> bulkCreate(
        @RequestParam("file") MultipartFile file
    );

    @GetMapping
    public abstract Page<EntityType> retrieve(
        EntityQueryFormType form
    );

    @PutMapping
    public abstract ResponseEntity<GenericResponse> update(
        @PathVariable Integer id,
        @RequestBody EntityType givenEntity
    );

    @DeleteMapping
    @Transactional
    public abstract ResponseEntity<GenericResponse> delete(
        @PathVariable List<Integer> ids
    );
}
