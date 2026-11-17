package com.project.my_landing_page.http.converters;

import com.project.my_landing_page.http.request.SobreMimModelRequest;
import com.project.my_landing_page.models.SobreMimModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SobreMimModelConverterTest {

    private final SobreMimModelConverter sobreMimModelConverter = new SobreMimModelConverter();

    @Test
    void deveConverterRequestParaEntidade() {
        SobreMimModelRequest req = new SobreMimModelRequest("Teste",
                10, "Teste1", "Teste2", "Teste3");

        SobreMimModel model = sobreMimModelConverter.toEntity(req);

        assertThat(model.getNome()).isEqualTo("Teste");
        assertThat(model.getIdade()).isEqualTo(10);
        assertThat(model.getCarreira()).isEqualTo("Teste1");
        assertThat(model.getProfissao()).isEqualTo("Teste2");
        assertThat(model.getEmpresa()).isEqualTo("Teste3");
    }

    @Test
    void deveConverterEntidadeParaRespoonse() {
        SobreMimModel model = new SobreMimModel();
        model.setId(10L);
        model.setNome("Teste Nome");
        model.setIdade(12);
        model.setCarreira("Teste Carreira");
        model.setProfissao("Teste Profissao");
        model.setEmpresa("Teste Empresa");

        sobreMimModelConverter.toResponse(model);

        assertThat(model.getId()).isEqualTo(10L);
        assertThat(model.getNome()).isEqualTo("Teste Nome");
        assertThat(model.getIdade()).isEqualTo(12);
        assertThat(model.getCarreira()).isEqualTo("Teste Carreira");
        assertThat(model.getProfissao()).isEqualTo("Teste Profissao");
        assertThat(model.getEmpresa()).isEqualTo("Teste Empresa");
    }
}
