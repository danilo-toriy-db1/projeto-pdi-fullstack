package com.project.my_landing_page.controllers.controllers;

import com.project.my_landing_page.DTOs.UsuarioDTO;
import com.project.my_landing_page.controllers.DefaultCrudControllerImpl;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.services.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController extends DefaultCrudControllerImpl<UsuarioDTO, Usuario> {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController (UsuarioService usuarioService) {
        super(usuarioService, UsuarioDTO::fromEntity, UsuarioDTO::toEntity);
    }
}
