package com.project.my_landing_page.controllers;

import com.project.my_landing_page.config.security.SecurityConfig;
import com.project.my_landing_page.http.converters.UsuarioConverter;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.UsersRoles;
import com.project.my_landing_page.services.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@Import({SecurityConfig.class, UsuarioConverter.class})
@ActiveProfiles("test")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    private static final String JSON_USUARIO = """
            {
                "usuario": "novo",
                "senha": "123",
                "role": "ADMIN"
            }
            """;

    private static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor comRole(String role) {
        return jwt()
                .jwt(token -> token.subject("1").claim("role", role))
                .authorities(new SimpleGrantedAuthority("ROLE_" + role));
    }

    private Usuario criarUsuario(Long id, String nome) {
        return new Usuario(id, nome, "hashDaSenha", LocalDateTime.of(2024, 1, 1, 0, 0), UsersRoles.ADMIN, Set.of());
    }

    @Test
    void deveCriarUsuarioERetornar201ComLocationSemSenha() throws Exception {
        when(usuarioService.save(any(Usuario.class))).thenReturn(criarUsuario(5L, "novo"));

        mockMvc.perform(post("/api/usuarios")
                        .with(comRole("SUPER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_USUARIO))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/usuarios/5"))
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.usuario").value("novo"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.senha").doesNotExist());

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioService).save(captor.capture());
        assertEquals("novo", captor.getValue().getUsuario());
        assertEquals("123", captor.getValue().getSenha());
        assertEquals(UsersRoles.ADMIN, captor.getValue().getRole());
    }

    @Test
    void deveListarUsuariosERetornar200() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(criarUsuario(1L, "um"), criarUsuario(2L, "dois")));

        mockMvc.perform(get("/api/usuarios")
                        .with(comRole("SUPER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].usuario").value("um"))
                .andExpect(jsonPath("$[1].usuario").value("dois"))
                .andExpect(jsonPath("$[0].senha").doesNotExist());
    }

    @Test
    void deveRetornar200QuandoUsuarioExistir() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(criarUsuario(1L, "um")));

        mockMvc.perform(get("/api/usuarios/1")
                        .with(comRole("SUPER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuario").value("um"));
    }

    @Test
    void deveRetornar404QuandoUsuarioNaoExistir() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/1").with(comRole("SUPER")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar200AoAtualizarQuandoExistir() throws Exception {
        when(usuarioService.update(eq(1L), any(Usuario.class))).thenReturn(criarUsuario(1L, "novo"));

        mockMvc.perform(put("/api/usuarios/1")
                        .with(comRole("SUPER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_USUARIO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value("novo"));
    }

    @Test
    void deveRetornar404AoAtualizarQuandoNaoExistir() throws Exception {
        when(usuarioService.update(eq(1L), any(Usuario.class))).thenReturn(null);

        mockMvc.perform(put("/api/usuarios/1")
                        .with(comRole("SUPER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_USUARIO))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar204AoDeletar() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1")
                        .with(comRole("SUPER")))
                .andExpect(status().isNoContent());

        verify(usuarioService).deleteById(1L);
    }

    @Test
    void deveRetornar403QuandoRoleForAdmin() throws Exception {
        mockMvc.perform(get("/api/usuarios").with(comRole("ADMIN")))
                .andExpect(status().isForbidden());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveRetornar401SemToken() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(usuarioService);
    }
}
