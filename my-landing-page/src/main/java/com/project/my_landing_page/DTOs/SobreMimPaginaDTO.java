package com.project.my_landing_page.DTOs;

import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.models.Desgosto;
import com.project.my_landing_page.models.Hobbie;
import com.project.my_landing_page.models.Objetivo;
import com.project.my_landing_page.models.SobreMimModel;
import com.project.my_landing_page.models.SobreMimPagina;
import com.project.my_landing_page.models.Usuario;

public record SobreMimPaginaDTO(Long id,
                                SobreMimModel sobreMimModel,
                                Hobbie hobbie,
                                Objetivo objetivo,
                                Desgosto desgosto,
                                Biografia biografia,
                                Usuario adminId) {
    public static SobreMimPaginaDTO fromEntity(SobreMimPagina sobreMimPagina) {
        return new SobreMimPaginaDTO(
                sobreMimPagina.getId(),
                sobreMimPagina.getSobreMimModel(),
                sobreMimPagina.getHobbie(),
                sobreMimPagina.getObjetivo(),
                sobreMimPagina.getDesgosto(),
                sobreMimPagina.getBiografia(),
                sobreMimPagina.getAdminId()
        );
    }

    public static SobreMimPagina toEntity(SobreMimPaginaDTO sobreMimPaginaDTO){
        return new SobreMimPagina(
                sobreMimPaginaDTO.id() != null ? sobreMimPaginaDTO.id() : 0L,
                sobreMimPaginaDTO.adminId(),
                sobreMimPaginaDTO.sobreMimModel(),
                sobreMimPaginaDTO.biografia(),
                sobreMimPaginaDTO.desgosto(),
                sobreMimPaginaDTO.hobbie(),
                sobreMimPaginaDTO.objetivo()
        );
    }
}
