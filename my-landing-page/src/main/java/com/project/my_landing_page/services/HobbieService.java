package com.project.my_landing_page.services;

import com.project.my_landing_page.models.Hobbie;
import com.project.my_landing_page.repositories.HobbieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HobbieService {

    @Autowired
    private HobbieRepository hobbieRepository;

    public Hobbie saveHobbie(Hobbie hobbie){
        return hobbieRepository.save(hobbie);
    }

    public List<Hobbie> findAllHobbies(){
        return hobbieRepository.findAll();
    }

    public Optional<Hobbie> findHobbieById(Long id){
        return hobbieRepository.findById(id);
    }

    public void deleteByIdHobbie (Long id){
        hobbieRepository.deleteById(id);
    }

    public Hobbie updateHobbie(Long id, Hobbie hobbie){
        Hobbie existingHobbie = hobbieRepository.findById(id).orElse(null);
        if(existingHobbie == null){
            return null;
        }

        existingHobbie.setHobbie(hobbie.getHobbie());

        return hobbieRepository.save(existingHobbie);
    }
}
