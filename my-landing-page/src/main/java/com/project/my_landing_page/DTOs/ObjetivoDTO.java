package com.project.my_landing_page.DTOs;

import com.project.my_landing_page.models.Objetivo;

public record ObjetivoDTO(Long id, String objetivo) {
    public static ObjetivoDTO fromEntity(Objetivo objetivo) {
        return new ObjetivoDTO(
                objetivo.getId(),
                objetivo.getObjetivo()
        );
    }

    public static Objetivo toEntity(ObjetivoDTO objetivoDTO){
        return new Objetivo(
                objetivoDTO.id() != null ? objetivoDTO.id() : 0L,
                objetivoDTO.objetivo()
        );
    }
}
