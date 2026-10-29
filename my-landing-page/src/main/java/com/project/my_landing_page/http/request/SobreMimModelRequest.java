package com.project.my_landing_page.http.request;

public record SobreMimModelRequest(
        String nome,
        int idade,
        String carreira,
        String profissao,
        String empresa
) {}
