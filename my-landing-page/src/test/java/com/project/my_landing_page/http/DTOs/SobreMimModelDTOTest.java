package com.project.my_landing_page.http.DTOs;

import com.project.my_landing_page.models.SobreMimModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SobreMimModelDTOTest {
    @Test
    void deveConverterEntidadeParaDto() {
        SobreMimModel entidade = new SobreMimModel(1L, "Nome", 1, "Carreira", "Profissao", "Empresa");

        SobreMimModelDTO dto = SobreMimModelDTO.fromEntity(entidade);

        assertEquals(new SobreMimModelDTO(1L, "Nome", 1, "Carreira", "Profissao", "Empresa"), dto);
    }

    @Test
    void deveConverterDtoParaEntidadeMantendoId() {
        SobreMimModelDTO dto = new SobreMimModelDTO(1L, "Nome", 1, "Carreira", "Profissao", "Empresa");

        SobreMimModel entidade = SobreMimModelDTO.toEntity(dto);

        assertEquals(new SobreMimModel(1L, "Nome", 1, "Carreira", "Profissao", "Empresa"), entidade);
    }

    @Test
    void deveUsarIdZeroQuandoIdDoDtoForNulo() {
        SobreMimModelDTO dto = new SobreMimModelDTO(null, "Nome", 1, "Carreira", "Profissao", "Empresa");

        SobreMimModel entidade = SobreMimModelDTO.toEntity(dto);

        assertEquals(0L, entidade.getId());
        assertEquals("Nome", entidade.getNome());
        assertEquals(1, entidade.getIdade());
        assertEquals("Carreira", entidade.getCarreira());
        assertEquals("Profissao", entidade.getProfissao());
        assertEquals("Empresa", entidade.getEmpresa());
    }
}
