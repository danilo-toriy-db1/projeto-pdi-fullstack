package com.project.my_landing_page.repositories.repositories;

import com.project.my_landing_page.models.SobreMimPagina;
import com.project.my_landing_page.repositories.DefaultRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface SobreMimPaginaRepository extends DefaultRepository<SobreMimPagina> {

    boolean existsByAdminId(Long adminId);

    Optional<SobreMimPagina> findByAdminId(Long adminId);

    @Override
    @EntityGraph(attributePaths = {"admin", "admin.habilidades", "sobreMimModel", "biografia", "objetivo", "desgosto", "hobbie"})
    List<SobreMimPagina> findAll();
}
