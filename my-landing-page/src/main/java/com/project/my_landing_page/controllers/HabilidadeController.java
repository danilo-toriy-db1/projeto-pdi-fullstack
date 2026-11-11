package com.project.my_landing_page.controllers;

import com.project.my_landing_page.http.DTOs.HabilidadeDTO;
import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.services.services.HabilidadeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habilidades")
public class HabilidadeController extends DefaultCrudControllerImpl<HabilidadeDTO, Habilidade> {
    public HabilidadeController (HabilidadeService habilidadeService) {
        super(habilidadeService, HabilidadeDTO::fromEntity, HabilidadeDTO::toEntity);
    }
}