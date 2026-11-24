package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.SobreMimModel;
import com.project.my_landing_page.repositories.repositories.SobreMimModelRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SobreMimModelService extends DefaultServiceImpl<SobreMimModel> {

    private final SobreMimModelRepository sobreMimModelRepository;

    public SobreMimModelService (SobreMimModelRepository sobreMimModelRepository) {
        super(sobreMimModelRepository);
        this.sobreMimModelRepository = sobreMimModelRepository;
    }

    @Override
    public SobreMimModel update(Long id, SobreMimModel sobreMimModel){
        SobreMimModel existingSobreMimModel = sobreMimModelRepository.findById(id).orElse(null);
        if(existingSobreMimModel == null){
            return null;
        }

        existingSobreMimModel.setNome(sobreMimModel.getNome());
        existingSobreMimModel.setIdade(sobreMimModel.getIdade());
        existingSobreMimModel.setCarreira(sobreMimModel.getCarreira());
        existingSobreMimModel.setProfissao(sobreMimModel.getProfissao());
        existingSobreMimModel.setEmpresa(sobreMimModel.getEmpresa());

        return sobreMimModelRepository.save(existingSobreMimModel);
    }
}
