package com.project.my_landing_page.repositories;

import com.project.my_landing_page.models.Biografia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiografiaRepository extends JpaRepository<Biografia, Long> {
}
