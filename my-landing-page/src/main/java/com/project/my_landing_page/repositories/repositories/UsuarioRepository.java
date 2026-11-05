package com.project.my_landing_page.repositories.repositories;

import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.repositories.DefaultRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface UsuarioRepository extends DefaultRepository<Usuario> {

    @EntityGraph(attributePaths = "habilidades")
    Optional<Usuario> findWithHabilidadesById(Long id);
}
