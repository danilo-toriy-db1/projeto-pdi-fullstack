package com.project.my_landing_page.DTOs;

import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.UsersRoles;
import java.sql.Timestamp;

public record UsuarioDTO(Long id, String usuario, String senha, Timestamp data_cadastro, UsersRoles role) {
    public static UsuarioDTO fromEntity(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsuario(),
                usuario.getSenha(),
                usuario.getData_cadastro(),
                usuario.getRole()
        );
    }

    public static Usuario toEntity(UsuarioDTO usuarioDTO){
        return new Usuario(
                usuarioDTO.id() != null ? usuarioDTO.id() : 0L,
                usuarioDTO.usuario(),
                usuarioDTO.senha(),
                usuarioDTO.data_cadastro(),
                usuarioDTO.role()
        );
    }

}