package com.project.my_landing_page.controllers;

import com.project.my_landing_page.http.converters.UsuarioConverter;
import com.project.my_landing_page.http.request.UsuarioRequest;
import com.project.my_landing_page.http.response.UsuarioResponse;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.services.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController{

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioConverter converter;

    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@RequestBody UsuarioRequest request) {
        Usuario salvo = service.save(converter.toEntity(request));
        URI location = URI.create("/api/usuarios/" + salvo.getId());
        return ResponseEntity.created(location).body(converter.toResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        List<UsuarioResponse> entities = service.findAll().stream()
                .map(converter::toResponse)
                .toList();
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(converter::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id, @RequestBody UsuarioRequest request) {
        Usuario usuarioAtualizado = service.update(id, converter.toEntity(request));
        if (usuarioAtualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(converter.toResponse(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
