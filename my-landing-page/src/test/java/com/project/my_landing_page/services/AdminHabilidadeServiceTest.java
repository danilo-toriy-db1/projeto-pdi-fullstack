package com.project.my_landing_page.services;

import com.project.my_landing_page.http.response.AdminHabilidadesResponse;
import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.HabilidadesTipo;
import com.project.my_landing_page.repositories.repositories.HabilidadeRepository;
import com.project.my_landing_page.repositories.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.project.my_landing_page.mockData.UsuarioMock.getUsuarioMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminHabilidadeServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private HabilidadeRepository habilidadeRepository;

    @InjectMocks
    private AdminHabilidadeService adminHabilidadeService;

    @Test
    void deveRetornarHabilidadesDoUsuarioQuandoUsuarioExistir() {
        Habilidade habilidade = new Habilidade(1L, "Teste", HabilidadesTipo.HARD);
        Usuario usuario = getUsuarioMock(Optional.empty());
        usuario.getHabilidades().add(habilidade);
        when(usuarioRepository.findWithHabilidadesById(2L)).thenReturn(Optional.of(usuario));

        Optional<AdminHabilidadesResponse> response = adminHabilidadeService.getHabilidadeByUser(2L);

        assertThat(response).isPresent();
        assertThat(response.get().usuarioId()).isEqualTo(2L);
        assertThat(response.get().habilidades()).hasSize(1);
        assertThat(response.get().habilidades().get(0).id()).isEqualTo(1L);
        assertThat(response.get().habilidades().get(0).habilidade()).isEqualTo("Teste");
        assertThat(response.get().habilidades().get(0).tipo()).isEqualTo(HabilidadesTipo.HARD);
        verify(usuarioRepository).findWithHabilidadesById(2L);
        verifyNoMoreInteractions(usuarioRepository);
        verifyNoInteractions(habilidadeRepository);
    }

    @Test
    void deveRetornarVazioQuandoUsuarioNaoExistir() {
        when(usuarioRepository.findWithHabilidadesById(3L)).thenReturn(Optional.empty());

        Optional<AdminHabilidadesResponse> response = adminHabilidadeService.getHabilidadeByUser(3L);

        assertThat(response).isEmpty();
        verify(usuarioRepository).findWithHabilidadesById(3L);
        verifyNoMoreInteractions(usuarioRepository);
        verifyNoInteractions(habilidadeRepository);
    }

    @Test
    void deveRetornarVazioAoSalvarQuandoUsuarioNaoForEncontrado() {
        List<Long> habilidadesIds = List.of(1L, 2L);
        when(usuarioRepository.findById(4L)).thenReturn(Optional.empty());

        Optional<AdminHabilidadesResponse> response = adminHabilidadeService.saveHabilidadesByUser(4L, habilidadesIds);

        assertThat(response).isEmpty();
        verify(usuarioRepository).findById(4L);
        verifyNoMoreInteractions(usuarioRepository);
        verifyNoInteractions(habilidadeRepository);
    }

    @Test
    void deveLancarExcecaoQuandoAlgumaHabilidadeNaoExiste() {
        Habilidade habilidade = new Habilidade(1L, "Teste", HabilidadesTipo.HARD);
        Usuario usuario = getUsuarioMock(Optional.empty());
        usuario.getHabilidades().add(habilidade);
        List<Long> habilidadesIds = List.of(2L, 99L);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));
        when(habilidadeRepository.findAllById(habilidadesIds)).thenReturn(List.of(habilidade));


        Throwable erro = catchThrowable(() -> adminHabilidadeService.saveHabilidadesByUser(2L, habilidadesIds));

        assertThat(erro)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Alguma habilidade informada não existe");
        assertThat(usuario.getHabilidades()).hasSize(1);
    }

    @Test
    void deveRetornarHabilidadesQuandoUsuarioExistirEHabilidadesValidas() {
        Habilidade habilidade1 = new Habilidade(1L, "Teste", HabilidadesTipo.HARD);
        Habilidade habilidade2 = new Habilidade(2L, "Teste 2", HabilidadesTipo.SOFT);
        Usuario usuario = getUsuarioMock(Optional.empty());
        usuario.getHabilidades().add(habilidade1);
        usuario.getHabilidades().add(habilidade2);
        List<Long> habilidadesIds = List.of(1L, 2L);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));
        when(habilidadeRepository.findAllById(habilidadesIds)).thenReturn(List.of(habilidade1, habilidade2));

        Optional<AdminHabilidadesResponse> response = adminHabilidadeService.saveHabilidadesByUser(2L, habilidadesIds);

        assertThat(response).isPresent();
        verify(usuarioRepository).findById(2L);
        verify(habilidadeRepository).findAllById(habilidadesIds);
        verifyNoMoreInteractions(usuarioRepository);
        verifyNoMoreInteractions(habilidadeRepository);
    }
}
