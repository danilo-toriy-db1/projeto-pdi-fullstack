package com.project.my_landing_page.http.response;

import com.project.my_landing_page.http.DTOs.HabilidadeDTO;

import java.util.List;

public record SobreMimPaginaResponse(Long id,
                                     Long usuarioId,
                                     String admin,
                                     SobreMimModelResponse sobreMim,
                                     String biografia,
                                     String hobbie,
                                     String objetivo,
                                     String desgosto,
                                     List<HabilidadeDTO> habilidades
                                     ) {}
