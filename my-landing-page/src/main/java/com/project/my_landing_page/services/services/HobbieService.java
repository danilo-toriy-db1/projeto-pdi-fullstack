package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Hobbie;
import com.project.my_landing_page.repositories.repositories.HobbieRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class HobbieService extends DefaultServiceImpl<Hobbie> {

    @Autowired
    private HobbieRepository hobbieRepository;

    public HobbieService (HobbieRepository hobbieRepository) {
        super(hobbieRepository);
    }

    @Override
    public Hobbie update(@PathVariable Long id, @RequestBody Hobbie hobbie){
        Hobbie existingHobbie = hobbieRepository.findById(id).orElse(null);
        if(existingHobbie == null){
            return null;
        }

        existingHobbie.setHobbie(hobbie.getHobbie());

        return hobbieRepository.save(existingHobbie);
    }
}
