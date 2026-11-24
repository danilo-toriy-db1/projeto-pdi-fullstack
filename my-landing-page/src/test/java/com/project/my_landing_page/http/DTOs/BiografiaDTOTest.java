package com.project.my_landing_page.http.DTOs;

import com.project.my_landing_page.models.Biografia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiografiaDTOTest {

    @Test
    void deveConverterDtoParaEntidadeMantendoId() {
        BiografiaDTO dto = new BiografiaDTO(1L, "Biografia1");

        Biografia entidade = BiografiaDTO.toEntity(dto);

        assertEquals(new Biografia(1L, "Biografia1"), entidade);
    }

    @Test
    void deveConverterEntidadeParaDto() {
        Biografia entidade = new Biografia(1L, "Biografia1");

        BiografiaDTO dto = BiografiaDTO.fromEntity(entidade);

        assertEquals(new BiografiaDTO(1L, "Biografia1"), dto);
    }

    @Test
    void deveUsarIdZeroQuandoIdDoDtoForNulo() {
        BiografiaDTO dto = new BiografiaDTO(null, "Biografia1");

        Biografia entidade = BiografiaDTO.toEntity(dto);

        assertEquals(0L, entidade.getId());
        assertEquals("Biografia1", entidade.getBiografia());
    }
}
