package com.project.my_landing_page.controllers.controllers;

import com.project.my_landing_page.DTOs.ObjetivoDTO;
import com.project.my_landing_page.controllers.DefaultCrudControllerImpl;
import com.project.my_landing_page.models.Objetivo;
import com.project.my_landing_page.services.services.ObjetivoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/objetivo")
public class ObjetivoController extends DefaultCrudControllerImpl<ObjetivoDTO, Objetivo> {
    public ObjetivoController (ObjetivoService objetivoService) {
        super(objetivoService, ObjetivoDTO::fromEntity, ObjetivoDTO::toEntity);
    }
}
