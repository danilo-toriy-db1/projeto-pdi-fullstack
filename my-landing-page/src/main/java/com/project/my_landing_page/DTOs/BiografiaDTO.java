package com.project.my_landing_page.DTOs;

import com.project.my_landing_page.models.Biografia;

public record BiografiaDTO(Long id, String biografia) {
    public static BiografiaDTO fromEntity(Biografia biografia) {
        return new BiografiaDTO(
                biografia.getId(),
                biografia.getBiografia()
        );
    }

    public static Biografia toEntity(BiografiaDTO biografiaDTO){
        return new Biografia(
                biografiaDTO.id() != null ? biografiaDTO.id() : 0L,
                biografiaDTO.biografia()
        );
    }
}
