package com.project.my_landing_page.services;

import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.repositories.BiografiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BiografiaService {

    @Autowired
    private BiografiaRepository biografiaRepository;

    public Biografia saveBiografia(Biografia biografia){
        return biografiaRepository.save(biografia);
    }

    public List<Biografia> findAllBiografias(){
        return biografiaRepository.findAll();
    }

    public Optional<Biografia> findBiografiaById(Long id){
        return biografiaRepository.findById(id);
    }

    public void deleteByIdBiografia(Long id){
        biografiaRepository.deleteById(id);
    }

    public Biografia updateBiografia(Long id, Biografia biografia){
        Biografia existingBiografia = biografiaRepository.findById(id).orElse(null);
        if(existingBiografia == null){
            return null;
        }

        existingBiografia.setBiografia(biografia.getBiografia());

        return biografiaRepository.save(existingBiografia);
    }
}
