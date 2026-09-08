package com.project.my_landing_page.services;

import com.project.my_landing_page.models.SobreMimModel;
import com.project.my_landing_page.repositories.SobreMimModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SobreMimModelService {

    @Autowired
    private SobreMimModelRepository sobreMimModelRepository;

    public SobreMimModel saveSobreMimModel(SobreMimModel sobreMimModel){
        return sobreMimModelRepository.save(sobreMimModel);
    }

    public List<SobreMimModel> findAllSobreMimModels(){
        return sobreMimModelRepository.findAll();
    }

    public Optional<SobreMimModel> findSobreMimModelById(Long id){
        return sobreMimModelRepository.findById(id);
    }

    public void deleteByIdSobreMimModel(Long id){
        sobreMimModelRepository.deleteById(id);
    }

    public SobreMimModel updateSobreMimModel(Long id, SobreMimModel sobreMimModel){
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
