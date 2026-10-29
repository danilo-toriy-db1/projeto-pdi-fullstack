package com.project.my_landing_page.services.services;

import com.project.my_landing_page.http.converters.SobreMimPaginaConverter;
import com.project.my_landing_page.http.request.SobreMimPaginaRequest;
import com.project.my_landing_page.http.response.SobreMimPaginaResponse;
import com.project.my_landing_page.models.SobreMimPagina;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.UsersRoles;
import com.project.my_landing_page.repositories.repositories.SobreMimPaginaRepository;
import com.project.my_landing_page.repositories.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SobreMimPaginaService {

    @Autowired
    private SobreMimPaginaRepository sobreMimPaginaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SobreMimPaginaConverter converter;

    @Transactional
    public SobreMimPaginaResponse criarPagina(Long adminId, SobreMimPaginaRequest request) {
        Usuario admin = usuarioRepository.findById(adminId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário administrador não encontrado (ID: " + adminId + ")"));

        if (admin.getRole() == UsersRoles.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não possui permissão para criar página");
        }

        if (sobreMimPaginaRepository.existsByAdminId(adminId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Página já existe para o administrador (ID: " + adminId + ")");
        }

        SobreMimPagina pagina = converter.toEntity(request, admin);
        SobreMimPagina salvo = sobreMimPaginaRepository.save(pagina);
        return converter.toResponse(salvo);
    }

    @Transactional
    public List<SobreMimPaginaResponse> listarPaginas() {
        return sobreMimPaginaRepository.findAll().stream()
                .map(converter::toResponse)
                .toList();
    }
}
