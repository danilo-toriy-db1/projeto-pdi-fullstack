package com.project.my_landing_page.http.DTOs;

import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.models.enums.HabilidadesTipo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HabilidadeDTOTest {

    @Test
    void deveConverterDtoParaEntidadeMantendoId() {
        HabilidadeDTO dto = new HabilidadeDTO(1L, "Habilidade", HabilidadesTipo.HARD);

        Habilidade entidade = HabilidadeDTO.toEntity(dto);

        assertEquals(new Habilidade(1L, "Habilidade", HabilidadesTipo.HARD), entidade);
    }

    @Test
    void deveConverterEntidadeParaDto() {
        Habilidade entidade = new Habilidade(1L, "Habilidade", HabilidadesTipo.HARD);

        HabilidadeDTO dto = HabilidadeDTO.fromEntity(entidade);

        assertEquals(new HabilidadeDTO(1L, "Habilidade", HabilidadesTipo.HARD), dto);
    }

    @Test
    void deveUsarIdZeroQuandoIdDoDtoForNulo() {
        HabilidadeDTO dto = new HabilidadeDTO(null, "Habilidade", HabilidadesTipo.SOFT);

        Habilidade entidade = HabilidadeDTO.toEntity(dto);

        assertEquals(0L, entidade.getId());
        assertEquals("Habilidade", entidade.getHabilidade());
        assertEquals(HabilidadesTipo.SOFT, entidade.getTipo());
    }
}
