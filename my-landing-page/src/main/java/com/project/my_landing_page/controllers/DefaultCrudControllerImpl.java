package com.project.my_landing_page.controllers;

import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.function.Function;

public abstract class DefaultCrudControllerImpl<DTO, Entity> implements DefaultCrudController<DTO> {

    private final DefaultServiceImpl<Entity> service;
    private final Function<Entity, DTO> fromEntity;
    private final Function<DTO, Entity> toEntity;

    protected DefaultCrudControllerImpl (DefaultServiceImpl<Entity> service,
                                         Function<Entity, DTO> fromEntity,
                                         Function<DTO, Entity> toEntity) {
        this.service = service;
        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
    }

    public ResponseEntity<DTO> save(@RequestBody DTO dto) {
        Entity entity = service.save(toEntity.apply(dto));
        return ResponseEntity.ok(fromEntity.apply(entity));
    }

    @Override
    public ResponseEntity<List<DTO>> findAll() {
        List<Entity> entities = service.findAll();
        return ResponseEntity.ok(entities.stream()
                .map(fromEntity)
                .toList());
    }

    @Override
    public ResponseEntity<DTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(entity -> ResponseEntity.ok(fromEntity.apply(entity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<DTO> update(@PathVariable Long id, @RequestBody DTO dto) {
        Entity updatedEntity = service.update(id, toEntity.apply(dto));
        if (updatedEntity == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fromEntity.apply(updatedEntity));
    }

    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
