package com.project.my_landing_page.http.converters;

import com.project.my_landing_page.http.request.UsuarioRequest;
import com.project.my_landing_page.http.response.UsuarioResponse;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.UsersRoles;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioConverterTest {

    private final UsuarioConverter usuarioConverter = new UsuarioConverter();

    @Test
    void deveConverterRequestParaEntidade() {
        UsuarioRequest req = new UsuarioRequest("Usuario", "Senha", UsersRoles.USER);

        Usuario model = usuarioConverter.toEntity(req);

        assertThat(model.getUsuario()).isEqualTo("Usuario");
        assertThat(model.getSenha()).isEqualTo("Senha");
        assertThat(model.getRole()).isEqualTo(UsersRoles.USER);
        assertThat(model.getData_cadastro()).isNotNull();
    }

    @Test
    void deveConverterEntidadeParaResponse() {
        Usuario model = new Usuario();
        model.setId(10L);
        model.setUsuario("Usuario");
        model.setSenha("Senha");
        model.setData_cadastro(LocalDateTime.now());
        model.setRole(UsersRoles.USER);
        model.setHabilidades(Set.of());

        UsuarioResponse response = usuarioConverter.toResponse(model);

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.usuario()).isEqualTo("Usuario");
        assertThat(response.role()).isEqualTo(UsersRoles.USER);
        assertThat(response.dataCadastro()).isNotNull();
    }
}
