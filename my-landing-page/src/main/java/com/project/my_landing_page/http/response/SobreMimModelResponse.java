package com.project.my_landing_page.http.response;

public record SobreMimModelResponse(Long id,
                                    String nome,
                                    Integer idade,
                                    String carreira,
                                    String profissao,
                                    String empresa
) {}
