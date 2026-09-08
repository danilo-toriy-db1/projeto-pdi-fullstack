package com.project.my_landing_page.services;

import com.project.my_landing_page.models.Desgosto;
import com.project.my_landing_page.repositories.DesgostoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesgostoService {

    @Autowired
    private DesgostoRepository desgostoRepository;

    public Desgosto saveDesgosto(Desgosto desgosto){
        return desgostoRepository.save(desgosto);
    }

    public List<Desgosto> findAllDesgostos(){
        return desgostoRepository.findAll();
    }

    public Optional<Desgosto> findDesgostoById(Long id){
        return desgostoRepository.findById(id);
    }

    public void deleteByIdDesgosto(Long id){
        desgostoRepository.deleteById(id);
    }

    public Desgosto updateDesgosto(Long id, Desgosto desgosto){
        Desgosto existingDesgosto = desgostoRepository.findById(id).orElse(null);
        if (existingDesgosto == null){
            return null;
        }

        existingDesgosto.setDesgosto(desgosto.getDesgosto());

        return desgostoRepository.save(existingDesgosto);
    }
}
