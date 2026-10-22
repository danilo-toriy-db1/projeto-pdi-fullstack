package com.project.my_landing_page.controllers.controllers;

import com.project.my_landing_page.DTOs.HobbieDTO;
import com.project.my_landing_page.controllers.DefaultCrudControllerImpl;
import com.project.my_landing_page.models.Hobbie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hobbie")
public class HobbieController extends DefaultCrudControllerImpl<HobbieDTO, Hobbie> {
    public HobbieController (com.project.my_landing_page.services.services.HobbieService hobbieService) {
        super(hobbieService, HobbieDTO::fromEntity, HobbieDTO::toEntity);
    }
}
