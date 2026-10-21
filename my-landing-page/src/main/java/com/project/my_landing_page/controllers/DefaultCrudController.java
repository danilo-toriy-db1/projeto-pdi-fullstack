package com.project.my_landing_page.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public abstract class DefaultCrudController<DTO> {
    @PostMapping
    public abstract ResponseEntity<DTO> save (@RequestBody DTO dto);

    @GetMapping
    public abstract ResponseEntity<List<DTO>> findAll();

    @GetMapping("/{id}")
    public abstract ResponseEntity<DTO> findById(@PathVariable Long id);

    @PutMapping("/{id}")
    public abstract ResponseEntity<DTO> update(@PathVariable Long id, @RequestBody DTO dto);

    @DeleteMapping("/{id}")
    public abstract ResponseEntity<Void> deleteById(@PathVariable Long id);
}
