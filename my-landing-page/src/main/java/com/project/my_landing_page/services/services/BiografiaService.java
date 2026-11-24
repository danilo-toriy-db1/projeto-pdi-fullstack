package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.repositories.repositories.BiografiaRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BiografiaService extends DefaultServiceImpl<Biografia> {

    private final BiografiaRepository biografiaRepository;

    public BiografiaService (BiografiaRepository biografiaRepository) {
        super(biografiaRepository);
        this.biografiaRepository = biografiaRepository;
    }

    @Override
    public Biografia update(Long id, Biografia biografia){
        Biografia existingBiografia = biografiaRepository.findById(id).orElse(null);
        if(existingBiografia == null){
            return null;
        }

        existingBiografia.setBiografia(biografia.getBiografia());

        return biografiaRepository.save(existingBiografia);
    }
}
