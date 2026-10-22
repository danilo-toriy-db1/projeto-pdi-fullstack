package com.project.my_landing_page.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface DefaultCrudController<DTO> {
    @PostMapping
    ResponseEntity<DTO> save (DTO dto);

    @GetMapping
    ResponseEntity<List<DTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<DTO> findById(Long id);

    @PutMapping("/{id}")
    ResponseEntity<DTO> update(Long id, DTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(Long id);
}
