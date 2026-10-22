package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.repositories.repositories.BiografiaRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BiografiaService extends DefaultServiceImpl<Biografia> {

    @Autowired
    private BiografiaRepository biografiaRepository;

    public BiografiaService (BiografiaRepository biografiaRepository) {
        super(biografiaRepository);
    }

    @Override
    public Biografia update(@PathVariable Long id, @RequestBody Biografia biografia){
        Biografia existingBiografia = biografiaRepository.findById(id).orElse(null);
        if(existingBiografia == null){
            return null;
        }

        existingBiografia.setBiografia(biografia.getBiografia());

        return biografiaRepository.save(existingBiografia);
    }
}
