package com.project.my_landing_page.controllers.controllers;

import com.project.my_landing_page.DTOs.DesgostoDTO;
import com.project.my_landing_page.controllers.DefaultCrudControllerImpl;
import com.project.my_landing_page.models.Desgosto;
import com.project.my_landing_page.services.services.DesgostoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/desgosto")
public class DesgostoController extends DefaultCrudControllerImpl<DesgostoDTO, Desgosto> {
    public DesgostoController (DesgostoService desgostoService) {
        super(desgostoService, DesgostoDTO::fromEntity, DesgostoDTO::toEntity);
    }
}
