package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.repositories.repositories.HabilidadeRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HabilidadeService extends DefaultServiceImpl<Habilidade> {

    private final HabilidadeRepository habilidadeRepository;

    public HabilidadeService(HabilidadeRepository repository) {
        super(repository);
        this.habilidadeRepository = repository;
    }

    @Override
    public Habilidade update(Long id, Habilidade habilidade) {
        Habilidade existingHabilidade = habilidadeRepository.findById(id).orElse(null);
        if(existingHabilidade == null){
            return null;
        }
        existingHabilidade.setHabilidade(habilidade.getHabilidade());
        existingHabilidade.setTipo(habilidade.getTipo());

        return habilidadeRepository.save(existingHabilidade);
    }

}
