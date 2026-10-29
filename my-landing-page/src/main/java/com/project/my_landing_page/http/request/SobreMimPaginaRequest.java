package com.project.my_landing_page.http.request;

public record SobreMimPaginaRequest(
        SobreMimModelRequest sobreMim,
        String biografia,
        String hobbie,
        String objetivo,
        String desgosto
) {}
