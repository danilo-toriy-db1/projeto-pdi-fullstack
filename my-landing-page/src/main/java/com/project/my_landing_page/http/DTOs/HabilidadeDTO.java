package com.project.my_landing_page.http.DTOs;

import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.models.enums.HabilidadesTipo;

public record HabilidadeDTO(Long id, String habilidade, HabilidadesTipo tipo) {
    public static HabilidadeDTO fromEntity(Habilidade habilidade) {
        return new HabilidadeDTO(
                habilidade.getId(),
                habilidade.getHabilidade(),
                habilidade.getTipo()
        );
    }

    public static Habilidade toEntity(HabilidadeDTO habilidadeDTO){
        return new Habilidade(
                habilidadeDTO.id() != null ? habilidadeDTO.id() : 0L,
                habilidadeDTO.habilidade(),
                habilidadeDTO.tipo()
        );
    }
}
