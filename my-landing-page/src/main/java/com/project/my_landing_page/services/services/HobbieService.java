package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Hobbie;
import com.project.my_landing_page.repositories.repositories.HobbieRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HobbieService extends DefaultServiceImpl<Hobbie> {

    private final HobbieRepository hobbieRepository;

    public HobbieService (HobbieRepository hobbieRepository) {
        super(hobbieRepository);
        this.hobbieRepository = hobbieRepository;
    }

    @Override
    public Hobbie update(Long id, Hobbie hobbie){
        Hobbie existingHobbie = hobbieRepository.findById(id).orElse(null);
        if(existingHobbie == null){
            return null;
        }

        existingHobbie.setHobbie(hobbie.getHobbie());

        return hobbieRepository.save(existingHobbie);
    }
}
