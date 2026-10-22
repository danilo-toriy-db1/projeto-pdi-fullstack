package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Desgosto;
import com.project.my_landing_page.repositories.repositories.DesgostoRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DesgostoService extends DefaultServiceImpl<Desgosto> {

    @Autowired
    private DesgostoRepository desgostoRepository;

    public DesgostoService (DesgostoRepository desgostoRepository) {
        super(desgostoRepository);
    }

    @Override
    public Desgosto update(@PathVariable Long id, @RequestBody Desgosto desgosto){
        Desgosto existingDesgosto = desgostoRepository.findById(id).orElse(null);
        if (existingDesgosto == null){
            return null;
        }

        existingDesgosto.setDesgosto(desgosto.getDesgosto());

        return desgostoRepository.save(existingDesgosto);
    }
}
