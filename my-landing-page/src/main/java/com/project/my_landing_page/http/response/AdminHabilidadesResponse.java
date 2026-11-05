package com.project.my_landing_page.http.response;

import com.project.my_landing_page.http.DTOs.HabilidadeDTO;
import com.project.my_landing_page.models.Usuario;

import java.util.List;

public record AdminHabilidadesResponse(Long usuarioId,
                                       String admin,
                                       List<HabilidadeDTO> habilidades) {

    public static AdminHabilidadesResponse toResponse(Usuario usuario) {
        return new AdminHabilidadesResponse(
                usuario.getId(),
                usuario.getUsuario(),
                usuario.getHabilidades().stream().map(HabilidadeDTO::fromEntity).toList()
        );
    }
}
