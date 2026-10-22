package com.project.my_landing_page.services;

import com.project.my_landing_page.repositories.DefaultRepository;

import java.util.List;
import java.util.Optional;

public class DefaultServiceImpl<Entity> implements DefaultService<Entity> {

    protected DefaultRepository<Entity> defaultRepository;

    protected DefaultServiceImpl (DefaultRepository<Entity> repository) {
        this.defaultRepository = repository;
    }

    @Override
    public Entity save(Entity entity) {
        return defaultRepository.save(entity);
    }

    @Override
    public List<Entity> findAll() {
        return defaultRepository.findAll();
    }

    @Override
    public Optional<Entity> findById(Long id) {
        return defaultRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        defaultRepository.deleteById(id);
    }

    @Override
    public Entity update(Long id, Entity entity) {
        throw new RuntimeException("Método não implementado");
    }
}
