package com.project.my_landing_page.http.DTOs;

import com.project.my_landing_page.models.Hobbie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HobbieDTOTest {

    @Test
    void deveConverterEntidadeParaDto() {
        Hobbie entidade = new Hobbie(1L, "Hobbie1");

        HobbieDTO dto = HobbieDTO.fromEntity(entidade);

        assertEquals(new HobbieDTO(1L, "Hobbie1"), dto);
    }

    @Test
    void deveConverterDtoParaEntidadeMantendoId() {
        HobbieDTO dto = new HobbieDTO(1L, "Hobbie1");

        Hobbie entidade = HobbieDTO.toEntity(dto);

        assertEquals(new Hobbie(1L, "Hobbie1"), entidade);
    }

    @Test
    void deveUsarIdZeroQuandoIdDoDtoForNulo() {
        HobbieDTO dto = new HobbieDTO(null, "Hobbie1");

        Hobbie entidade = HobbieDTO.toEntity(dto);

        assertEquals(0L, entidade.getId());
        assertEquals("Hobbie1", entidade.getHobbie());
    }
}
