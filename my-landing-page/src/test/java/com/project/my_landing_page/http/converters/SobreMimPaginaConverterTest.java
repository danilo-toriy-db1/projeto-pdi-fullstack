package com.project.my_landing_page.http.converters;

import com.project.my_landing_page.http.request.SobreMimModelRequest;
import com.project.my_landing_page.http.request.SobreMimPaginaRequest;
import com.project.my_landing_page.http.response.SobreMimModelResponse;
import com.project.my_landing_page.http.response.SobreMimPaginaResponse;
import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.models.Desgosto;
import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.models.Hobbie;
import com.project.my_landing_page.models.Objetivo;
import com.project.my_landing_page.models.SobreMimModel;
import com.project.my_landing_page.models.SobreMimPagina;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.HabilidadesTipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SobreMimPaginaConverterTest {

    @Mock
    private SobreMimModelConverter sobreMimModelConverter;

    @InjectMocks
    private SobreMimPaginaConverter sobreMimPaginaConverter;

    private SobreMimModelRequest sobreMimModelRequest;
    private SobreMimPaginaRequest req;
    private Usuario admin;

    @BeforeEach
    void setUp() {
        sobreMimModelRequest = new SobreMimModelRequest("Teste", 10, "Teste1", "Teste2", "Teste3");
        req = new SobreMimPaginaRequest(
                sobreMimModelRequest,
                "Bio",
                "Hobbie",
                "Objetivo",
                "Desgosto",
                List.of()
        );
        admin = new Usuario();
        admin.setId(1L);
    }

    @Test
    void deveConverterRequestParaEntidade() {
        SobreMimModel model = new SobreMimModel();
        when(sobreMimModelConverter.toEntity(sobreMimModelRequest)).thenReturn(model);

        SobreMimPagina pagina = sobreMimPaginaConverter.toEntity(req, admin);

        assertThat(pagina.getAdmin()).isEqualTo(admin);
        assertThat(pagina.getSobreMimModel()).isEqualTo(model);
        assertThat(pagina.getBiografia().getBiografia()).isEqualTo(req.biografia());
        assertThat(pagina.getHobbie().getHobbie()).isEqualTo(req.hobbie());
        assertThat(pagina.getObjetivo().getObjetivo()).isEqualTo(req.objetivo());
        assertThat(pagina.getDesgosto().getDesgosto()).isEqualTo(req.desgosto());

        verify(sobreMimModelConverter).toEntity(sobreMimModelRequest);
        verifyNoMoreInteractions(sobreMimModelConverter);
    }

    @Test
    void deveCriarEntidadesMesmoComCamposNulosAtribuindoValoresDefault() {
        SobreMimPaginaRequest reqVazio = new SobreMimPaginaRequest(sobreMimModelRequest, null, null, null, null, List.of());
        when(sobreMimModelConverter.toEntity(sobreMimModelRequest)).thenReturn(new SobreMimModel());

        SobreMimPagina pagina = sobreMimPaginaConverter.toEntity(reqVazio, admin);

        assertThat(pagina.getBiografia()).isNotNull();
        assertThat(pagina.getHobbie()).isNotNull();
        assertThat(pagina.getObjetivo()).isNotNull();
        assertThat(pagina.getDesgosto()).isNotNull();
    }

    @Test
    void deveConverterEntidadeParaResponse() {
        Habilidade habilidade = new Habilidade(0L, "Teste", HabilidadesTipo.HARD);
        admin.setUsuario("Teste");
        admin.getHabilidades().add(habilidade);
        SobreMimModel model = new SobreMimModel();
        SobreMimModelResponse modelResponse = new SobreMimModelResponse(10L, "Teste", 10, "Teste1", "Teste2", "Teste3");
        when(sobreMimModelConverter.toResponse(model)).thenReturn(modelResponse);
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(99L);
        pagina.setAdmin(admin);
        pagina.setSobreMimModel(model);
        pagina.setBiografia(new Biografia(0L,"Bio"));
        pagina.setHobbie(new Hobbie(0L, "Hobbie"));
        pagina.setObjetivo(new Objetivo(0L, "Objetivo"));
        pagina.setDesgosto(new Desgosto(0L, "Desgosto"));

        SobreMimPaginaResponse response = sobreMimPaginaConverter.toResponse(pagina);

        assertThat(response.id()).isEqualTo(99L);
        assertThat(response.usuarioId()).isEqualTo(1L);
        assertThat(response.admin()).isEqualTo("Teste");
        assertThat(response.sobreMim()).isSameAs(modelResponse);
        assertThat(response.biografia()).isEqualTo("Bio");
        assertThat(response.hobbie()).isEqualTo("Hobbie");
        assertThat(response.objetivo()).isEqualTo("Objetivo");
        assertThat(response.desgosto()).isEqualTo("Desgosto");
        assertThat(response.habilidades()).hasSize(1);
        assertThat(response.habilidades().get(0).id()).isEqualTo(0L);
        assertThat(response.habilidades().get(0).habilidade()).isEqualTo("Teste");
        assertThat(response.habilidades().get(0).tipo()).isEqualTo(HabilidadesTipo.HARD);
        verify(sobreMimModelConverter).toResponse(model);
        verifyNoMoreInteractions(sobreMimModelConverter);
    }

    @Test
    void deveRetornarHabilidadesVaziasQuandoAdminNaoTiverHabilidades() {
        admin.setUsuario("teste");
        SobreMimModel model = new SobreMimModel();
        SobreMimModelResponse modelResponse = new SobreMimModelResponse(10L, "Teste", 10, "Teste1", "Teste2", "Teste3");
        when(sobreMimModelConverter.toResponse(model)).thenReturn(modelResponse);
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setId(99L);
        pagina.setAdmin(admin);
        pagina.setSobreMimModel(model);
        pagina.setBiografia(new Biografia(0L,"Bio"));
        pagina.setHobbie(new Hobbie(0L, "Hobbie"));
        pagina.setObjetivo(new Objetivo(0L, "Objetivo"));
        pagina.setDesgosto(new Desgosto(0L, "Desgosto"));

        SobreMimPaginaResponse response = sobreMimPaginaConverter.toResponse(pagina);

        assertThat(response.habilidades()).isEmpty();
        verify(sobreMimModelConverter).toResponse(model);
        verifyNoMoreInteractions(sobreMimModelConverter);
    }

    @Test
    void deveAtualizarCamposDaPaginaMantendoAsEntidades() {
        SobreMimModel model = new SobreMimModel();
        model.setId(10L);
        model.setNome("Teste Antigo");
        model.setIdade(20);
        model.setCarreira("Teste1 Antigo");
        model.setProfissao("Teste2 Antigo");
        model.setEmpresa("Teste3 Antigo");
        Biografia biografiaAntiga = new Biografia(0L,"Bio Antiga");
        Hobbie hobbieAntigo = new Hobbie(0L, "Hobbie Antigo");
        Objetivo objetivoAntigo = new Objetivo(0L, "Objetivo Antigo");
        Desgosto desgostoAntigo = new Desgosto(0L, "Desgosto Antigo");
        SobreMimPagina pagina = new SobreMimPagina();
        pagina.setSobreMimModel(model);
        pagina.setBiografia(biografiaAntiga);
        pagina.setHobbie(hobbieAntigo);
        pagina.setObjetivo(objetivoAntigo);
        pagina.setDesgosto(desgostoAntigo);

        sobreMimPaginaConverter.updateEntity(pagina, req);

        assertThat(pagina.getSobreMimModel().getNome()).isEqualTo("Teste");
        assertThat(pagina.getSobreMimModel().getIdade()).isEqualTo(10);
        assertThat(pagina.getSobreMimModel().getCarreira()).isEqualTo("Teste1");
        assertThat(pagina.getSobreMimModel().getProfissao()).isEqualTo("Teste2");
        assertThat(pagina.getSobreMimModel().getEmpresa()).isEqualTo("Teste3");
        assertThat(pagina.getBiografia().getBiografia()).isEqualTo("Bio");
        assertThat(pagina.getHobbie().getHobbie()).isEqualTo("Hobbie");
        assertThat(pagina.getObjetivo().getObjetivo()).isEqualTo("Objetivo");
        assertThat(pagina.getDesgosto().getDesgosto()).isEqualTo("Desgosto");

        assertThat(pagina.getSobreMimModel()).isSameAs(model);
        assertThat(pagina.getSobreMimModel().getId()).isEqualTo(10L);
        assertThat(pagina.getBiografia()).isSameAs(biografiaAntiga);
        assertThat(pagina.getHobbie()).isSameAs(hobbieAntigo);
        assertThat(pagina.getObjetivo()).isSameAs(objetivoAntigo);
        assertThat(pagina.getDesgosto()).isSameAs(desgostoAntigo);

        verifyNoMoreInteractions(sobreMimModelConverter);
    }
}
