package com.project.my_landing_page.services.services;

import com.project.my_landing_page.http.DTOs.HabilidadeDTO;
import com.project.my_landing_page.http.converters.SobreMimPaginaConverter;
import com.project.my_landing_page.http.request.SobreMimPaginaRequest;
import com.project.my_landing_page.http.response.SobreMimPaginaResponse;
import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.models.SobreMimPagina;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.HabilidadesTipo;
import com.project.my_landing_page.models.enums.UsersRoles;
import com.project.my_landing_page.repositories.repositories.HabilidadeRepository;
import com.project.my_landing_page.repositories.repositories.SobreMimPaginaRepository;
import com.project.my_landing_page.repositories.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.project.my_landing_page.mockData.UsuarioMock.getUsuarioMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SobreMimPaginaServiceTest {

    @Mock
    private SobreMimPaginaRepository sobreMimPaginaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private HabilidadeRepository habilidadeRepository;

    @Mock
    private SobreMimPaginaConverter converter;

    @InjectMocks
    private SobreMimPaginaService sobreMimPaginaService;

    @Test
    void deveLancarExcecaoQuandoUsuarioAdminNaoExistir() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable erro = catchThrowable(() -> sobreMimPaginaService.criarPagina(1L, null));

        assertThat(erro)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Usuário administrador não encontrado (ID: " + 1L + ")");
        assertThat(((ResponseStatusException) erro).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForAdmin() {
        Usuario usuario = getUsuarioMock(Optional.of(UsersRoles.USER));
        usuario.setSenha("Teste");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Throwable erro = catchThrowable(() -> sobreMimPaginaService.criarPagina(1L, null));

        assertThat(erro)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Usuário não possui permissão para criar página");
        assertThat(((ResponseStatusException) erro).getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void deveLancarExcecaoQuandoAdminJaPossuirUmaPagina() {
        Usuario usuario = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(sobreMimPaginaRepository.existsByAdminId(1L)).thenReturn(true);

        Throwable erro = catchThrowable(() -> sobreMimPaginaService.criarPagina(1L, null));

        assertThat(erro)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Página já existe para o administrador (ID: " + 1L + ")");
        assertThat(((ResponseStatusException) erro).getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void deveCriarPaginaComSucesso() {
        Usuario usuario = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        SobreMimPaginaRequest req = new SobreMimPaginaRequest(null, "Bio", "Hobbie", "Objetivo", "Desgosto", null);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));
        when(sobreMimPaginaRepository.existsByAdminId(2L)).thenReturn(false);
        when(converter.toEntity(req, usuario)).thenReturn(new SobreMimPagina());
        when(sobreMimPaginaRepository.save(any(SobreMimPagina.class))).thenReturn(new SobreMimPagina());
        when(converter.toResponse(any(SobreMimPagina.class)))
                .thenReturn(new SobreMimPaginaResponse(
                        1L, 2L, "Usuário", null, "Bio", "Hobbie", "Objetivo", "Desgosto", List.of()));

        SobreMimPaginaResponse response = sobreMimPaginaService.criarPagina(2L, req);

        assertThat(response).isNotNull();
        assertThat(response.usuarioId()).isEqualTo(2L);
        assertThat(response.admin()).isEqualTo("Usuário");
        assertThat(response.biografia()).isEqualTo("Bio");
        assertThat(response.hobbie()).isEqualTo("Hobbie");
        assertThat(response.objetivo()).isEqualTo("Objetivo");
        assertThat(response.desgosto()).isEqualTo("Desgosto");
        assertThat(response.habilidades()).isEmpty();
        verify(usuarioRepository).findById(2L);
        verify(sobreMimPaginaRepository).existsByAdminId(2L);
        verify(converter).toEntity(req, usuario);
        verify(sobreMimPaginaRepository).save(any(SobreMimPagina.class));
        verify(converter).toResponse(any(SobreMimPagina.class));
    }

    @Test
    void deveRetornarPaginaQuandoExistirPorId() {
        SobreMimPagina model = new SobreMimPagina();
        model.setId(1L);
        SobreMimPaginaResponse resEsperada = new SobreMimPaginaResponse(
                1L, 10L, "Teste", null, "Bio", "Hobbie", "Objetivo", "Desgosto", List.of());
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(model));
        when(converter.toResponse(model)).thenReturn(resEsperada);

        Optional<SobreMimPaginaResponse> response = sobreMimPaginaService.buscarPaginaPorId(1L);

        assertThat(response).containsSame(resEsperada);
        verify(sobreMimPaginaRepository).findById(1L);
        verify(converter).toResponse(model);
    }

    @Test
    void deveRetornarVazioQuandoPaginaNaoExistirPorId() {
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<SobreMimPaginaResponse> response = sobreMimPaginaService.buscarPaginaPorId(1L);

        assertThat(response).isNotPresent();
        verify(sobreMimPaginaRepository).findById(1L);
    }

    @Test
    void deveListarTodasAsPaginas() {
        SobreMimPagina model1 = new SobreMimPagina();
        model1.setId(1L);
        SobreMimPagina model2 = new SobreMimPagina();
        model2.setId(2L);
        when(sobreMimPaginaRepository.findAll()).thenReturn(List.of(model1, model2));
        when(converter.toResponse(model1)).thenReturn(new SobreMimPaginaResponse(
                1L, 2L, "Usuário", null, "Bio", "Desgosto", "Objetivo", "Hobbie", List.of()));
        when(converter.toResponse(model2)).thenReturn(new SobreMimPaginaResponse(
                3L, 4L, "Usuário", null, "Bio2", "Desgosto2", "Objetivo2", "Hobbie2", List.of()));

        List<SobreMimPaginaResponse> response = sobreMimPaginaService.listarPaginas();

        assertThat(response).hasSize(2);
        assertThat(response.get(0).id()).isEqualTo(1L);
        assertThat(response.get(1).id()).isEqualTo(3L);
        verify(sobreMimPaginaRepository).findAll();
        verify(converter).toResponse(model1);
        verify(converter).toResponse(model2);
    }

    @Test
    void deveLancarExcecaoAoAtualizarPaginaQueNaoExiste() {
        SobreMimPaginaRequest req = new SobreMimPaginaRequest(null, "Bio", "Hobbie", "Objetivo", "Desgosto", null);
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable erro = catchThrowable(() -> sobreMimPaginaService.atualizarPagina(1L, req, 2L, false));

        assertThat(erro)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Página não encontrada");
        assertThat(((ResponseStatusException) erro).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(sobreMimPaginaRepository).findById(1L);
        verify(converter, never()).updateEntity(any(), any());
        verifyNoInteractions(converter, habilidadeRepository);
    }

    @Test
    void deveLancarExcecaoAoAtualizarPaginaQuandoUsuarioNaoTiverPermissao() {
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        SobreMimPaginaRequest req = new SobreMimPaginaRequest(null, "Bio", "Hobbie", "Objetivo", "Desgosto", null);
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));

        Throwable erro = catchThrowable(() -> sobreMimPaginaService.atualizarPagina(1L, req, 3L, false));

        assertThat(erro)
                .isInstanceOf(AccessDeniedException.class)
                .hasMessageContaining("Você não tem permissão para alterar esta página");
        verify(sobreMimPaginaRepository).findById(1L);
        verify(converter, never()).updateEntity(any(), any());
        verifyNoInteractions(converter, habilidadeRepository);
    }

    @Test
    void deveAtualizarPaginaSemAlterarHabilidadesQuandoListaForNula() {
        Habilidade habilidade = new Habilidade(10L, "Antigo", HabilidadesTipo.SOFT);
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        admin.getHabilidades().add(habilidade);
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        SobreMimPaginaRequest req = new SobreMimPaginaRequest(null, "Bio", "Hobbie", "Objetivo", "Desgosto", null);
        SobreMimPaginaResponse resEsperada = new SobreMimPaginaResponse(1L, 10L, "Usuário", null, "Bio", "Desgosto", "Objetivo", "Hobbie", List.of());
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));
        when(converter.toResponse(pagina)).thenReturn(resEsperada);

        SobreMimPaginaResponse res = sobreMimPaginaService.atualizarPagina(1L, req, 2L, false);

        assertThat(res).isEqualTo(resEsperada);
        assertThat(admin.getHabilidades()).containsExactly(habilidade);
        verify(sobreMimPaginaRepository).findById(1L);
        verify(converter).updateEntity(pagina, req);
        verify(converter).toResponse(pagina);
        verifyNoInteractions(habilidadeRepository);
    }

    @Test
    void deveAtualizarPaginaQuandoUsuarioForSuperAdmin() {
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.SUPER));
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        SobreMimPaginaRequest req = new SobreMimPaginaRequest(null, "Bio", "Hobbie", "Objetivo", "Desgosto", null);
        SobreMimPaginaResponse resEsperada = new SobreMimPaginaResponse(1L, 10L, "Usuário", null, "Bio", "Desgosto", "Objetivo", "Hobbie", List.of());
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));
        when(converter.toResponse(pagina)).thenReturn(resEsperada);

        SobreMimPaginaResponse res = sobreMimPaginaService.atualizarPagina(1L, req, 3L, true);

        assertThat(res).isEqualTo(resEsperada);
        verify(converter).updateEntity(pagina, req);
    }

    @Test
    void deveReutilizarHabilidadeQuandoElaJaExistirNoBanco() {
        Habilidade habilidade = new Habilidade(10L, "Existente", HabilidadesTipo.HARD);
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        SobreMimPaginaRequest req = new SobreMimPaginaRequest(null, "Bio", "Hobbie", "Objetivo", "Desgosto",
                List.of(new HabilidadeDTO(null, "Existente", HabilidadesTipo.HARD)));
        SobreMimPaginaResponse resEsperada = new SobreMimPaginaResponse(1L, 10L, "Usuário", null, "Bio", "Desgosto", "Objetivo", "Hobbie", List.of());
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));
        when(habilidadeRepository.findByHabilidadeIgnoreCase("Existente")).thenReturn(Optional.of(habilidade));
        when(converter.toResponse(pagina)).thenReturn(resEsperada);

        SobreMimPaginaResponse res = sobreMimPaginaService.atualizarPagina(1L, req, 2L, false);

        assertThat(res).isEqualTo(resEsperada);
        assertThat(admin.getHabilidades()).containsExactly(habilidade);
        verify(habilidadeRepository).findByHabilidadeIgnoreCase("Existente");
        verify(habilidadeRepository, never()).save(any());
    }

    @Test
    void deveSalvarHabilidadeNovaQuandoElaNaoExistirNoBanco() {
        Habilidade novaHabilidade = new Habilidade(10L, "Novo", HabilidadesTipo.HARD);
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        SobreMimPaginaRequest req = new SobreMimPaginaRequest(null, "Bio", "Hobbie", "Objetivo", "Desgosto",
                List.of(new HabilidadeDTO(null, "Novo", HabilidadesTipo.HARD)));
        SobreMimPaginaResponse resEsperada = new SobreMimPaginaResponse(1L, 10L, "Usuário", null, "Bio", "Desgosto", "Objetivo", "Hobbie", List.of());
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));
        when(habilidadeRepository.findByHabilidadeIgnoreCase("Novo")).thenReturn(Optional.empty());
        when(habilidadeRepository.save(any(Habilidade.class))).thenReturn(novaHabilidade);
        when(converter.toResponse(pagina)).thenReturn(resEsperada);

        SobreMimPaginaResponse res = sobreMimPaginaService.atualizarPagina(1L, req, 2L, false);

        ArgumentCaptor<Habilidade> captor = ArgumentCaptor.forClass(Habilidade.class);
        verify(habilidadeRepository).save(captor.capture());
        assertThat(captor.getValue().getId()).isZero();
        assertThat(captor.getValue().getHabilidade()).isEqualTo("Novo");
        assertThat(captor.getValue().getTipo()).isEqualTo(HabilidadesTipo.HARD);
        assertThat(res).isEqualTo(resEsperada);
        assertThat(admin.getHabilidades()).containsExactly(novaHabilidade);
    }

    @Test
    void deveLancarExcecaoAoDeletarPaginaQueNaoExiste() {
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable erro = catchThrowable(() -> sobreMimPaginaService.deletarPagina(1L, 2L, false));

        assertThat(erro).isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Página não encontrada");
        verify(sobreMimPaginaRepository).findById(1L);
        verify(sobreMimPaginaRepository, never()).delete(any());
    }

    @Test
    void deveDeletarPaginaComSucesso() {
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));

        sobreMimPaginaService.deletarPagina(1L, 2L, false);

        verify(sobreMimPaginaRepository).findById(1L);
        verify(sobreMimPaginaRepository).delete(pagina);
    }

    @Test
    void deveDeletarPaginaQuandoOUsuarioForSuperAdmin() {
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.SUPER));
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));

        sobreMimPaginaService.deletarPagina(1L, 2L, false);

        verify(sobreMimPaginaRepository).findById(1L);
        verify(sobreMimPaginaRepository).delete(pagina);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoTiverPermissao() {
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));

        Throwable erro = catchThrowable(() -> sobreMimPaginaService.deletarPagina(1L, 3L, false));

        assertThat(erro)
                .isInstanceOf(AccessDeniedException.class)
                .hasMessageContaining("Você não tem permissão para alterar esta página");
        verify(sobreMimPaginaRepository).findById(1L);
        verify(sobreMimPaginaRepository, never()).delete(any());
    }

    @Test
    void deveBuscarHabilidadeSemEspacoNoNome() {
        Habilidade habilidade = new Habilidade(1L, "Teste", HabilidadesTipo.HARD);
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        SobreMimPaginaRequest req = new SobreMimPaginaRequest(null, "Bio", "Hobbie", "Objetivo", "Desgosto",
                List.of(new HabilidadeDTO(null, " Teste ", HabilidadesTipo.HARD)));
        SobreMimPaginaResponse resEsperado = new SobreMimPaginaResponse(
                1L, 2L, "Usuário", null, "Bio", "Hobbie", "Objetivo", "Desgosto", List.of());
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));
        when(habilidadeRepository.findByHabilidadeIgnoreCase("Teste")).thenReturn(Optional.of(habilidade));
        when(converter.toResponse(pagina)).thenReturn(resEsperado);

        SobreMimPaginaResponse res = sobreMimPaginaService.atualizarPagina(1L, req, 2L, false);

        assertThat(res).isEqualTo(resEsperado);
        assertThat(admin.getHabilidades()).containsExactly(habilidade);
        verify(habilidadeRepository).findByHabilidadeIgnoreCase("Teste");
    }

    @Test
    void deveRemoverTodasAsHabilidadesQuandoListaForVazia() {
        Usuario admin = getUsuarioMock(Optional.of(UsersRoles.ADMIN));
        Habilidade habilidade = new Habilidade(1L, "Teste", HabilidadesTipo.HARD);
        admin.getHabilidades().add(habilidade);
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(1L);
        pagina.setAdmin(admin);
        SobreMimPaginaRequest req = new SobreMimPaginaRequest(null, "Bio", "Hobbie", "Objetivo", "Desgosto", List.of());
        SobreMimPaginaResponse responseEsperado = new SobreMimPaginaResponse(
                1L, 2L, "Usuário", null, "Bio", "Hobbie", "Objetivo", "Desgosto", List.of());
        when(sobreMimPaginaRepository.findById(1L)).thenReturn(Optional.of(pagina));
        when(converter.toResponse(pagina)).thenReturn(responseEsperado);

        SobreMimPaginaResponse res = sobreMimPaginaService.atualizarPagina(1L, req, 2L, false);

        assertThat(res).isEqualTo(responseEsperado);
        assertThat(admin.getHabilidades()).isEmpty();
        verifyNoInteractions(habilidadeRepository);
    }
}
