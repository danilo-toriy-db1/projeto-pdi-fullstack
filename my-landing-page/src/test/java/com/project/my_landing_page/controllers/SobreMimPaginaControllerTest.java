package com.project.my_landing_page.controllers;

import com.project.my_landing_page.config.security.SecurityConfig;
import com.project.my_landing_page.http.request.SobreMimPaginaRequest;
import com.project.my_landing_page.http.response.SobreMimPaginaResponse;
import com.project.my_landing_page.services.services.SobreMimPaginaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.project.my_landing_page.mockData.SobreMimPaginaMock.getJsonCompleto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SobreMimPaginaController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public class SobreMimPaginaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SobreMimPaginaService sobreMimPaginaService;

    @Test
    void deveCriarPaginaERetornar201ComUri() throws Exception {
        SobreMimPaginaResponse res = new SobreMimPaginaResponse(
                1L, 2L, "Teste", null, "Bio", "Hobbie", "Objetivo", "Desgosto", List.of());
        when(sobreMimPaginaService.criarPagina(eq(1L), any(SobreMimPaginaRequest.class))).thenReturn(res);
        String json = getJsonCompleto();

        mockMvc.perform(post("/api/paginas/cadastrar/1")
                .with(jwt()
                        .jwt(token -> token.subject("10").claim("role", "ADMIN"))
                        .authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.admin").value("Teste"))
                .andExpect(jsonPath("$.usuarioId").value(2L))
                .andExpect(jsonPath("$.biografia").value("Bio"))
                .andExpect(jsonPath("$.hobbie").value("Hobbie"))
                .andExpect(jsonPath("$.objetivo").value("Objetivo"))
                .andExpect(jsonPath("$.desgosto").value("Desgosto"))
                .andExpect(jsonPath("$.habilidades").isEmpty()
                );

        verify(sobreMimPaginaService).criarPagina(eq(1L), any(SobreMimPaginaRequest.class));
    }

    @Test
    void deveListarTodasAsPaginasERetornar200() throws Exception {
        SobreMimPaginaResponse pagina1 = new SobreMimPaginaResponse(
                1L, 3L, "admin1", null, "Bio1", "Hobbie1", "Objetivo1", "Desgosto1", List.of());
        SobreMimPaginaResponse pagina2 = new SobreMimPaginaResponse(
                2L, 4L, "admin2", null, "Bio2", "Hobbie2", "Objetivo2", "Desgosto2", List.of());
        when(sobreMimPaginaService.listarPaginas()).thenReturn(List.of(pagina1, pagina2));

        mockMvc.perform(get("/api/paginas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].admin").value("admin1"))
                .andExpect(jsonPath("$[0].usuarioId").value(3L))
                .andExpect(jsonPath("$[0].biografia").value("Bio1"))
                .andExpect(jsonPath("$[0].hobbie").value("Hobbie1"))
                .andExpect(jsonPath("$[0].objetivo").value("Objetivo1"))
                .andExpect(jsonPath("$[0].desgosto").value("Desgosto1"))
                .andExpect(jsonPath("$[0].habilidades").isEmpty())
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].admin").value("admin2"))
                .andExpect(jsonPath("$[1].usuarioId").value(4L))
                .andExpect(jsonPath("$[1].biografia").value("Bio2"))
                .andExpect(jsonPath("$[1].hobbie").value("Hobbie2"))
                .andExpect(jsonPath("$[1].objetivo").value("Objetivo2"))
                .andExpect(jsonPath("$[1].desgosto").value("Desgosto2"))
                .andExpect(jsonPath("$[1].habilidades").isEmpty());

        verify(sobreMimPaginaService).listarPaginas();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverPaginas() throws Exception {
        when(sobreMimPaginaService.listarPaginas()).thenReturn(List.of());

        mockMvc.perform(get("/api/paginas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()
                );
    }

    @Test
    void deveRetornar200ComPaginaQuandoExistir() throws Exception {
        SobreMimPaginaResponse res = new SobreMimPaginaResponse(
                1L, 2L, "Teste", null, "Bio", "Hobbie", "Objetivo", "Desgosto", List.of());
        when(sobreMimPaginaService.buscarPaginaPorId(1L)).thenReturn(Optional.of(res));

        mockMvc.perform(get("/api/paginas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.admin").value("Teste"))
                .andExpect(jsonPath("$.biografia").value("Bio"))
                .andExpect(jsonPath("$.hobbie").value("Hobbie"))
                .andExpect(jsonPath("$.objetivo").value("Objetivo"))
                .andExpect(jsonPath("$.desgosto").value("Desgosto"))
                .andExpect(jsonPath("$.habilidades").isEmpty());
    }

    @Test
    void deveRetornar404QuandoPaginaNaoExistir() throws Exception {
        when(sobreMimPaginaService.buscarPaginaPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/paginas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarRepassandoUsuarioDoToken() throws Exception {
        SobreMimPaginaResponse response = new SobreMimPaginaResponse(
                1L, 2L, "Teste", null, "Bio", "Hobbie", "Objetivo", "Desgosto", List.of());
        when(sobreMimPaginaService.atualizarPagina(eq(1L), any(SobreMimPaginaRequest.class), eq(10L), eq(false)))
                .thenReturn(response);
        String json = getJsonCompleto();
        mockMvc.perform(put("/api/paginas/1")
                    .with(jwt()
                            .jwt(token -> token.subject("10").claim("role", "ADMIN"))
                            .authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.biografia").value("Bio"))
                .andExpect(jsonPath("$.hobbie").value("Hobbie"))
                .andExpect(jsonPath("$.objetivo").value("Objetivo"))
                .andExpect(jsonPath("$.desgosto").value("Desgosto"))
                .andExpect(jsonPath("$.habilidades").isEmpty());

        verify(sobreMimPaginaService).atualizarPagina(eq(1L), any(SobreMimPaginaRequest.class), eq(10L), eq(false));
    }

    @Test
    void deveIdentificarSuperAdminPeloClaimDoToken() throws Exception {
        mockMvc.perform(delete("/api/paginas/1")
                        .with(jwt()
                                .jwt(token -> token.subject("20").claim("role", "SUPER"))
                                .authorities(new SimpleGrantedAuthority("ROLE_SUPER"))))
                .andExpect(status().isNoContent());

        verify(sobreMimPaginaService).deletarPagina(1L, 20L, true);
    }

    @Test
    void deveRetornar204AoDeletar() throws Exception {
        mockMvc.perform(delete("/api/paginas/1")
                        .with(jwt()
                                .jwt(token -> token.subject("10").claim("role", "ADMIN"))
                                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isNoContent());

        verify(sobreMimPaginaService).deletarPagina(1L, 10L, false);
    }

    @Test
    void deveRetornar403QuandoServiceNegarPermissao() throws Exception {
        doThrow(new AccessDeniedException("Você não tem permissão para alterar esta página"))
                .when(sobreMimPaginaService).deletarPagina(1L, 20L, false);

        mockMvc.perform(delete("/api/paginas/1")
                        .with(jwt()
                                .jwt(token -> token.subject("20").claim("role", "ADMIN"))
                                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void deveRetornar401AoDeletarSemToken() throws Exception {
        mockMvc.perform(delete("/api/paginas/1"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(sobreMimPaginaService);
    }

    @Test
    void deveRetornar403AoDeletarComRoleUser() throws Exception {
        mockMvc.perform(delete("/api/paginas/1")
                        .with(jwt()
                                .jwt(token -> token.subject("30").claim("role", "USER"))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isForbidden());

        verifyNoInteractions(sobreMimPaginaService);
    }
}
