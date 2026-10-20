package com.project.my_landing_page.DTOs;

import com.project.my_landing_page.models.Desgosto;

public record DesgostoDTO(Long id, String desgosto) {
    public static DesgostoDTO fromEntity(Desgosto desgosto) {
        return new DesgostoDTO(
                desgosto.getId(),
                desgosto.getDesgosto()
        );
    }

    public static Desgosto toEntity(DesgostoDTO desgostoDTO){
        return new Desgosto(
                desgostoDTO.id() != null ? desgostoDTO.id() : 0L,
                desgostoDTO.desgosto()
        );
    }
}
