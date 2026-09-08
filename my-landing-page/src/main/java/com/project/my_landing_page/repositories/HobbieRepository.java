package com.project.my_landing_page.repositories;

import com.project.my_landing_page.models.Hobbie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbieRepository extends JpaRepository<Hobbie, Long> {
}
