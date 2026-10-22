package com.project.my_landing_page.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DefaultRepository<Entity> extends JpaRepository<Entity, Long> {
}
