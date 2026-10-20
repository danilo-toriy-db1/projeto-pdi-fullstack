package com.project.my_landing_page.controllers;

import com.project.my_landing_page.DTOs.UsuarioDTO;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.services.UsuarioService;
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

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> saveUsuario (@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioService.saveUsuario(UsuarioDTO.toEntity(usuarioDTO));
        return ResponseEntity.ok(UsuarioDTO.fromEntity(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAllUsuarios(){
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        return ResponseEntity.ok(usuarios.stream()
                .map(UsuarioDTO::fromEntity)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findUsuarioById(@PathVariable Long id){
        return usuarioService.findUsuarioById(id)
                .map(usuario -> ResponseEntity.ok(UsuarioDTO.fromEntity(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO){
        Usuario updatedUsuario = usuarioService.updateUsuario(id, UsuarioDTO.toEntity(usuarioDTO));
        if (updatedUsuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UsuarioDTO.fromEntity(updatedUsuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuarioById(@PathVariable Long id){
        usuarioService.deleteByIdUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
