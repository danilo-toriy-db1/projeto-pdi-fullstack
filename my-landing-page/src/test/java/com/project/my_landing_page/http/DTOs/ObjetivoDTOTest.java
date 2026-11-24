package com.project.my_landing_page.http.DTOs;

import com.project.my_landing_page.models.Objetivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjetivoDTOTest {
    @Test
    void deveConverterEntidadeParaDto() {
        Objetivo entidade = new Objetivo(1L, "Objetivo1");

        ObjetivoDTO dto = ObjetivoDTO.fromEntity(entidade);

        assertEquals(new ObjetivoDTO(1L, "Objetivo1"), dto);
    }

    @Test
    void deveConverterDtoParaEntidadeMantendoId() {
        ObjetivoDTO dto = new ObjetivoDTO(1L, "Objetivo1");

        Objetivo entidade = ObjetivoDTO.toEntity(dto);

        assertEquals(new Objetivo(1L, "Objetivo1"), entidade);
    }

    @Test
    void deveUsarIdZeroQuandoIdDoDtoForNulo() {
        ObjetivoDTO dto = new ObjetivoDTO(null, "Objetivo1");

        Objetivo entidade = ObjetivoDTO.toEntity(dto);

        assertEquals(0L, entidade.getId());
        assertEquals("Objetivo1", entidade.getObjetivo());
    }
}
