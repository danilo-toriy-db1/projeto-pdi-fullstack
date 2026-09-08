package com.project.my_landing_page.repositories;

import com.project.my_landing_page.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
