package com.project.my_landing_page.controllers;

import com.project.my_landing_page.http.request.AdminHabilidadesRequest;
import com.project.my_landing_page.http.response.AdminHabilidadesResponse;
import com.project.my_landing_page.services.AdminHabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminHabilidadeController {

    @Autowired
    private AdminHabilidadeService adminHabilidadeService;

    @GetMapping("/{id}/habilidades")
    public ResponseEntity<AdminHabilidadesResponse> getAllHabilidadesByUserId(@PathVariable Long id) {
        return adminHabilidadeService.getHabilidadeByUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/habilidades")
    public ResponseEntity<AdminHabilidadesResponse> saveHabilidadesByUser(@PathVariable Long id, @RequestBody AdminHabilidadesRequest req) {
        return adminHabilidadeService.saveHabilidadesByUser(id, req.habilidadesId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
