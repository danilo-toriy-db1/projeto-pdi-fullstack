package com.project.my_landing_page.http.converters;

import com.project.my_landing_page.http.request.UsuarioRequest;
import com.project.my_landing_page.http.response.UsuarioResponse;
import com.project.my_landing_page.models.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UsuarioConverter {

    public Usuario toEntity(UsuarioRequest req) {
        Usuario usuario = new Usuario();

        usuario.setUsuario(req.usuario());
        usuario.setData_cadastro(LocalDateTime.now());
        usuario.setSenha(req.senha());
        usuario.setRole(req.role());

        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsuario(),
                usuario.getData_cadastro(),
                usuario.getRole()
        );
    }
}
