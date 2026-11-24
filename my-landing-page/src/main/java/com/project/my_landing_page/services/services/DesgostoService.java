package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Desgosto;
import com.project.my_landing_page.repositories.repositories.DesgostoRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DesgostoService extends DefaultServiceImpl<Desgosto> {

    private final DesgostoRepository desgostoRepository;

    public DesgostoService (DesgostoRepository desgostoRepository) {
        super(desgostoRepository);
        this.desgostoRepository = desgostoRepository;
    }

    @Override
    public Desgosto update(Long id, Desgosto desgosto){
        Desgosto existingDesgosto = desgostoRepository.findById(id).orElse(null);
        if (existingDesgosto == null){
            return null;
        }

        existingDesgosto.setDesgosto(desgosto.getDesgosto());

        return desgostoRepository.save(existingDesgosto);
    }
}
