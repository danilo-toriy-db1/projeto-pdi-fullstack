package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Objetivo;
import com.project.my_landing_page.repositories.repositories.ObjetivoRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ObjetivoService extends DefaultServiceImpl<Objetivo> {

    @Autowired
    private ObjetivoRepository objetivoRepository;

    public ObjetivoService (ObjetivoRepository objetivoRepository) {
        super(objetivoRepository);
    }

    @Override
    public Objetivo update(@PathVariable Long id, @RequestBody Objetivo objetivo){
        Objetivo existingObjetivo = objetivoRepository.findById(id).orElse(null);
        if(existingObjetivo == null){
            return null;
        }

        existingObjetivo.setObjetivo(objetivo.getObjetivo());

        return objetivoRepository.save(existingObjetivo);
    }
}
