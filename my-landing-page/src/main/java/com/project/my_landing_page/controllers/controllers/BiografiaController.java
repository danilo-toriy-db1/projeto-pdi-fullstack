package com.project.my_landing_page.controllers.controllers;

import com.project.my_landing_page.DTOs.BiografiaDTO;
import com.project.my_landing_page.controllers.DefaultCrudControllerImpl;
import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.services.services.BiografiaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/biografia")
public class BiografiaController extends DefaultCrudControllerImpl<BiografiaDTO, Biografia> {
    public BiografiaController (BiografiaService biografiaService) {
        super(biografiaService, BiografiaDTO::fromEntity, BiografiaDTO::toEntity);
    }
}
