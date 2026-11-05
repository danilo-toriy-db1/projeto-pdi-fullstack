package com.project.my_landing_page.services;

import com.project.my_landing_page.http.response.AdminHabilidadesResponse;
import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.repositories.repositories.HabilidadeRepository;
import com.project.my_landing_page.repositories.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.project.my_landing_page.http.response.AdminHabilidadesResponse.toResponse;

@Service
public class AdminHabilidadeService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HabilidadeRepository habilidadeRepository;

    @Transactional(readOnly = true)
    public Optional<AdminHabilidadesResponse> getHabilidadeByUser(Long usuarioId) {
        return usuarioRepository.findWithHabilidadesById(usuarioId)
                .map(AdminHabilidadesResponse::toResponse);
    }

    @Transactional
    public Optional<AdminHabilidadesResponse> saveHabilidadesByUser(Long usuarioId, List<Long> habilidadesIds) {
        return usuarioRepository.findById(usuarioId).map(usuario -> this.getHabilidadesByIds(habilidadesIds, usuario));
    }

    private AdminHabilidadesResponse getHabilidadesByIds(List<Long> habilidadeIds, Usuario usuario) {
        List<Habilidade> habilidades = habilidadeRepository.findAllById(habilidadeIds);
        if (habilidades.size() != new HashSet<>(habilidadeIds).size()) {
            throw new EntityNotFoundException("Alguma habilidade informada não existe");
        }

        usuario.getHabilidades().clear();
        usuario.getHabilidades().addAll(habilidades);
        return toResponse(usuario);
    }
}
