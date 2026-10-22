package com.project.my_landing_page.services;

import java.util.List;
import java.util.Optional;

public interface DefaultService<Entity> {

    Entity save(Entity entity);

    List<Entity> findAll();

    Optional<Entity> findById(Long id);

    void deleteById(Long id);

    Entity update(Long id, Entity entity);
}
