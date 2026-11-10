package com.project.my_landing_page.repositories.repositories;

import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.repositories.DefaultRepository;

import java.util.Optional;

public interface HabilidadeRepository extends DefaultRepository<Habilidade> {

    Optional<Habilidade> findByHabilidadeIgnoreCase(String habilidade);
}
