package com.project.my_landing_page.controllers.security;

import com.project.my_landing_page.config.security.SecurityConfig;
import com.project.my_landing_page.http.request.LoginRequest;
import com.project.my_landing_page.http.response.LoginResponse;
import com.project.my_landing_page.models.enums.UsersRoles;
import com.project.my_landing_page.services.security.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Test
    void deveRetornar200ComTokenQuandoLoginForValido() throws Exception {
        LoginResponse res = new LoginResponse("token", "Bearer", Instant.parse("2050-01-01T00:00:00Z"), 1L, "Usuario", UsersRoles.ADMIN);
        when(authService.login(new LoginRequest("Usuario", "senha"))).thenReturn(res);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"usuario": "Usuario", "senha": "senha"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"))
                .andExpect(jsonPath("$.tipo").value("Bearer"))
                .andExpect(jsonPath("$.expiracao").exists())
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.usuario").value("Usuario"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void deveRetornar401QuandoCredenciaisForemInvalidas() throws Exception {
        when(authService.login(any(LoginRequest.class))).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"usuario": "Usuario", "senha": "errada"}
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornar400QuandoJsonForInvalido() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ isso não é json"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(authService);
    }
}
