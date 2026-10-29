package com.project.my_landing_page.http.response;

public record SobreMimPaginaResponse(Long id,
                                     SobreMimModelResponse sobreMim,
                                     String biografia,
                                     String hobbie,
                                     String objetivo,
                                     String desgosto
) {}
