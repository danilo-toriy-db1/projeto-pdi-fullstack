package com.project.my_landing_page.controllers;

import com.project.my_landing_page.config.security.SecurityConfig;
import com.project.my_landing_page.http.response.AdminHabilidadesResponse;
import com.project.my_landing_page.services.AdminHabilidadeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@WebMvcTest(AdminHabilidadeController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public class AdminHabilidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminHabilidadeService adminHabilidadeService;

    private static final String JSON_HABILIDADES = """                                                                                                                                                                            
              {"habilidadesId": [1, 2]}
              """;

    private static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor comRole(String role) {
        return jwt()
                .jwt(token -> token.subject("10").claim("role", role))
                .authorities(new SimpleGrantedAuthority("ROLE_" + role));

    }

    @Test
    void deveRetornar200ComHabilidadesQuandoUsuarioExistir() throws Exception {
        when(adminHabilidadeService.getHabilidadeByUser(1L)).thenReturn(Optional.of(new AdminHabilidadesResponse(1L, "admin", List.of())));

        mockMvc.perform(get("/api/admin/1/habilidades").with(comRole("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.admin").value("admin"))
                .andExpect(jsonPath("$.habilidades").isEmpty());
    }

    @Test
    void deveRetornar404AoBuscarUsuarioENaoExistir() throws Exception {
        when(adminHabilidadeService.getHabilidadeByUser(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/admin/1/habilidades").with(comRole("USER")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar401AoBuscarSemToken() throws Exception {
        mockMvc.perform(get("/api/admin/1/habilidades"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(adminHabilidadeService);
    }

    @Test
    void deveRetornar200AoSalvarHabilidadesComoAdmin() throws Exception {
        when(adminHabilidadeService.saveHabilidadesByUser(eq(1L), any()))
                .thenReturn(Optional.of(new AdminHabilidadesResponse(1L, "admin", List.of())));

        mockMvc.perform(put("/api/admin/1/habilidades")
                        .with(comRole("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_HABILIDADES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(1L));

        verify(adminHabilidadeService).saveHabilidadesByUser(1L, List.of(1L, 2L));
    }

    @Test
    void deveRetornar404AoSalvarHabilidadesQuandoUsuarioNaoExistir() throws Exception {
        when(adminHabilidadeService.saveHabilidadesByUser(eq(1L), any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/admin/1/habilidades")
                        .with(comRole("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_HABILIDADES))
                    .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar403AoSalvarComRoleUser() throws Exception {
        mockMvc.perform(put("/api/admin/1/habilidades")
                        .with(comRole("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_HABILIDADES))
                .andExpect(status().isForbidden());

        verifyNoInteractions(adminHabilidadeService);
    }

    @Test
    void deveRetornar401AoSalvarSemToken() throws Exception {
        mockMvc.perform(put("/api/admin/1/habilidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_HABILIDADES))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(adminHabilidadeService);
    }


}
