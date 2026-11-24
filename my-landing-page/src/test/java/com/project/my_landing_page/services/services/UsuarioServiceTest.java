package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.UsersRoles;
import com.project.my_landing_page.repositories.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveSalvarUsuarioComSenhaCriptografada() {
        Usuario usuario = new Usuario();
        usuario.setUsuario("usuario");
        usuario.setSenha("senha");

        when(passwordEncoder.encode("senha")).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        usuarioService.save(usuario);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        Usuario salvo = captor.getValue();
        assertEquals("usuario", salvo.getUsuario());
        assertEquals("senhaCriptografada", salvo.getSenha());
    }

    @Test
    void deveRetornarNuloQuandoUsuarioNaoExistir() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Usuario res = usuarioService.update(1L, new Usuario());

        assertThat(res).isNull();
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveAtualizarUsuarioERoleQuandoExistir() {
        Usuario existente = new Usuario(1L, "Antigo", "Teste", null, UsersRoles.USER, Set.of());
        Usuario novo = new Usuario(null, "Novo", null, null, UsersRoles.ADMIN, Set.of());
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        usuarioService.update(1L, novo);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(captor.capture());
        Usuario salvo = captor.getValue();
        assertEquals(1L, salvo.getId());
        assertEquals("Novo", salvo.getUsuario());
        assertEquals(UsersRoles.ADMIN, salvo.getRole());
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveCriptografarSenhaQuandoForInformadaNovaSenha() {
        Usuario existente = new Usuario(1L, "Antigo", "antigo", null, UsersRoles.USER, Set.of());
        Usuario novo = new Usuario(null, "Antigo", "novo", null, UsersRoles.USER, Set.of());
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(passwordEncoder.encode("novo")).thenReturn("novaSenhaCriptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        usuarioService.update(1L, novo);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(captor.capture());
        verify(passwordEncoder).encode("novo");
        assertEquals("novaSenhaCriptografada", captor.getValue().getSenha());
    }

    @Test
    void deveAtualizarUsuarioSemSenhaQuandoSenhaNovaForNula() {
        Usuario existente = new Usuario(1L, "Antigo", "antigo", null, UsersRoles.USER, Set.of());
        Usuario novo = new Usuario(null, "Antigo", null, null, UsersRoles.USER, Set.of());
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        usuarioService.update(1L, novo);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(captor.capture());
        verify(passwordEncoder, never()).encode(any());
        assertEquals("antigo", captor.getValue().getSenha());
        assertEquals("Antigo", captor.getValue().getUsuario());
    }

    @Test
    void deveAtualizarUsuarioSemSenhaQuandoSenhaNovaForVazia() {
        Usuario existente = new Usuario(1L, "Antigo", "antigo", null, UsersRoles.USER, Set.of());
        Usuario novo = new Usuario(null, "Antigo", "", null, UsersRoles.USER, Set.of());
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        usuarioService.update(1L, novo);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(captor.capture());
        verify(passwordEncoder, never()).encode(any());
        assertEquals("antigo", captor.getValue().getSenha());
        assertEquals("Antigo", captor.getValue().getUsuario());
    }
}
