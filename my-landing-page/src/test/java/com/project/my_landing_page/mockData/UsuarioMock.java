package com.project.my_landing_page.mockData;

import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.UsersRoles;

import java.util.Optional;

public class UsuarioMock {
    public static Usuario getUsuarioMock(Optional<UsersRoles> role) {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setUsuario("Usuario");
        role.ifPresent(usuario::setRole);
        return usuario;
    }
}
