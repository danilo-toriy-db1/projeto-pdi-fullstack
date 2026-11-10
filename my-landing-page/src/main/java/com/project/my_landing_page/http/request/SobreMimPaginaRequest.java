package com.project.my_landing_page.http.request;

import com.project.my_landing_page.http.DTOs.HabilidadeDTO;

import java.util.List;

public record SobreMimPaginaRequest(
        SobreMimModelRequest sobreMim,
        String biografia,
        String hobbie,
        String objetivo,
        String desgosto,
        List<HabilidadeDTO> habilidades
) {}
