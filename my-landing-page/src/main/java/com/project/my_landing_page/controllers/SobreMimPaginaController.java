package com.project.my_landing_page.controllers;

import com.project.my_landing_page.http.request.SobreMimPaginaRequest;
import com.project.my_landing_page.http.response.SobreMimPaginaResponse;
import com.project.my_landing_page.services.services.SobreMimPaginaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/paginas")
public class SobreMimPaginaController {

    @Autowired
    private SobreMimPaginaService sobreMimPaginaService;

    @PostMapping("/cadastrar/{id}")
    public ResponseEntity<SobreMimPaginaResponse> cadastrarNovaPagina(@PathVariable Long id, @RequestBody SobreMimPaginaRequest sobreMimPaginaRequest) {
        SobreMimPaginaResponse resposta = sobreMimPaginaService.criarPagina(id, sobreMimPaginaRequest);
        URI location = URI.create("/api/paginas/" + resposta.id());
        return ResponseEntity.created(location).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<SobreMimPaginaResponse>> listarPaginas() {
        return ResponseEntity.ok(sobreMimPaginaService.listarPaginas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SobreMimPaginaResponse> buscarPaginaPorId(@PathVariable Long id) {
        return sobreMimPaginaService.buscarPaginaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SobreMimPaginaResponse> atualizarPagina(@PathVariable Long id, @RequestBody SobreMimPaginaRequest req, @AuthenticationPrincipal Jwt jwt) {
        Long usuarioId = Long.valueOf(jwt.getSubject());
        boolean isSuper = "SUPER".equalsIgnoreCase(jwt.getClaimAsString("role"));

        SobreMimPaginaResponse respostaAtualizada = sobreMimPaginaService.atualizarPagina(id, req, usuarioId, isSuper);
        return ResponseEntity.ok(respostaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPagina(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long usuarioId = Long.valueOf(jwt.getSubject());
        boolean isSuper = "SUPER".equalsIgnoreCase(jwt.getClaimAsString("role"));

        sobreMimPaginaService.deletarPagina(id, usuarioId, isSuper);
        return ResponseEntity.noContent().build();
    }
}
