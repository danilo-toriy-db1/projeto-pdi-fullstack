package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Objetivo;
import com.project.my_landing_page.repositories.repositories.ObjetivoRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ObjetivoService extends DefaultServiceImpl<Objetivo> {

    private final ObjetivoRepository objetivoRepository;

    public ObjetivoService (ObjetivoRepository objetivoRepository) {
        super(objetivoRepository);
        this.objetivoRepository = objetivoRepository;
    }

    @Override
    public Objetivo update(Long id, Objetivo objetivo){
        Objetivo existingObjetivo = objetivoRepository.findById(id).orElse(null);
        if(existingObjetivo == null){
            return null;
        }

        existingObjetivo.setObjetivo(objetivo.getObjetivo());

        return objetivoRepository.save(existingObjetivo);
    }
}
