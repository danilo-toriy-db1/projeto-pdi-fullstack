package com.project.my_landing_page.services.security;

import com.project.my_landing_page.http.request.LoginRequest;
import com.project.my_landing_page.http.response.LoginResponse;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.UsersRoles;
import com.project.my_landing_page.repositories.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        when(passwordEncoder.encode("senhaFalsa")).thenReturn("senhaFalsa");
        authService = new AuthService(usuarioRepository, passwordEncoder, tokenService);
    }

    @Test
    void deveRetornarTokenQuandoAsCredenciaisForemValidas() {
        Usuario usuario = new Usuario(1L, "Usuario", "senha", null, UsersRoles.ADMIN, Set.of());
        Instant expiration = Instant.parse("2050-01-01T00:00:00Z");
        when(usuarioRepository.findByUsuario("Usuario")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha", "senha")).thenReturn(true);
        when(tokenService.gerarToken(usuario)).thenReturn(new TokenGerado("tokenTeste", expiration));

        LoginResponse res = authService.login(new LoginRequest("Usuario", "senha"));

        verify(usuarioRepository).findByUsuario("Usuario");
        verify(passwordEncoder).matches("senha", "senha");
        verify(tokenService).gerarToken(usuario);
        assertEquals("tokenTeste", res.token());
        assertEquals("Bearer", res.tipo());
        assertEquals(expiration, res.expiracao());
        assertEquals(1L, res.usuarioId());
        assertEquals("Usuario", res.usuario());
        assertEquals(UsersRoles.ADMIN, res.role());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaForInvalida() {
        Usuario usuario = new Usuario(1L, "Usuario", "senha", null, UsersRoles.USER, Set.of());
        when(usuarioRepository.findByUsuario("Usuario")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaInvalida", "senha")).thenReturn(false);

        Throwable erro = catchThrowable(() -> authService.login(new LoginRequest("Usuario", "senhaInvalida")));

        assertThat(erro).isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Credenciais inválidas");
        assertThat(((ResponseStatusException) erro).getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(usuarioRepository).findByUsuario("Usuario");
        verify(passwordEncoder).matches("senhaInvalida", "senha");
        verify(tokenService, never()).gerarToken(any());
    }

    @Test
    void deveUsarHashFalsoDaSenhaQuandoUsuarioNaoForEncontrado() {
        when(usuarioRepository.findByUsuario("Usuario")).thenReturn(Optional.empty());
        when(passwordEncoder.matches("senhaInvalida", "senhaFalsa")).thenReturn(false);

        Throwable erro = catchThrowable(() -> authService.login(new LoginRequest("Usuario", "senhaInvalida")));

        assertThat(erro).isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Credenciais inválidas");
        assertThat(((ResponseStatusException) erro).getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(usuarioRepository).findByUsuario("Usuario");
        verify(passwordEncoder).matches("senhaInvalida", "senhaFalsa");
        verify(tokenService, never()).gerarToken(any());
    }
}
