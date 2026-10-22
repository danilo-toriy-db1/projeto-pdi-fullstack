package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Objetivo;
import com.project.my_landing_page.repositories.repositories.ObjetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjetivoService {

    @Autowired
    private ObjetivoRepository objetivoRepository;

    public Objetivo saveObjetivo(Objetivo objetivo) {
        return objetivoRepository.save(objetivo);
    }

    public List<Objetivo> findAllObjetivos(){
        return objetivoRepository.findAll();
    }

    public Optional<Objetivo> findObjetivoById(Long id){
        return objetivoRepository.findById(id);
    }

    public void deleteByIdObjetivo(Long id){
        objetivoRepository.deleteById(id);
    }

    public Objetivo updateObjetivo(Long id, Objetivo objetivo){
        Objetivo existingObjetivo = objetivoRepository.findById(id).orElse(null);
        if(existingObjetivo == null){
            return null;
        }

        existingObjetivo.setObjetivo(objetivo.getObjetivo());

        return objetivoRepository.save(existingObjetivo);
    }
}
