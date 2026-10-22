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
public class UsuarioController extends DefaultCrudControllerImpl<UsuarioDTO, Usuario> {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController (UsuarioService usuarioService) {
        super(usuarioService, UsuarioDTO::fromEntity, UsuarioDTO::toEntity);
    }
}
