package com.project.my_landing_page.http.DTOs;

import com.project.my_landing_page.models.Desgosto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DesgostoDTOTest {
    @Test
    void deveConverterEntidadeParaDto() {
        Desgosto entidade = new Desgosto(1L, "Desgosto1");

        DesgostoDTO dto = DesgostoDTO.fromEntity(entidade);

        assertEquals(new DesgostoDTO(1L, "Desgosto1"), dto);
    }

    @Test
    void deveConverterDtoParaEntidadeMantendoId() {
        DesgostoDTO dto = new DesgostoDTO(1L, "Desgosto1");

        Desgosto entidade = DesgostoDTO.toEntity(dto);

        assertEquals(new Desgosto(1L, "Desgosto1"), entidade);
    }

    @Test
    void deveUsarIdZeroQuandoIdDoDtoForNulo() {
        DesgostoDTO dto = new DesgostoDTO(null, "Desgosto1");

        Desgosto entidade = DesgostoDTO.toEntity(dto);

        assertEquals(0L, entidade.getId());
        assertEquals("Desgosto1", entidade.getDesgosto());
    }
}
