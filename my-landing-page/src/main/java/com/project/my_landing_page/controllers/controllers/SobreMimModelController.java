package com.project.my_landing_page.controllers.controllers;

import com.project.my_landing_page.DTOs.SobreMimModelDTO;
import com.project.my_landing_page.controllers.DefaultCrudControllerImpl;
import com.project.my_landing_page.models.SobreMimModel;
import com.project.my_landing_page.services.services.SobreMimModelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sobre-mim")
public class SobreMimModelController extends DefaultCrudControllerImpl<SobreMimModelDTO, SobreMimModel> {
    public SobreMimModelController (SobreMimModelService sobreMimModelService) {
        super(sobreMimModelService, SobreMimModelDTO::fromEntity, SobreMimModelDTO::toEntity);
    }

}
