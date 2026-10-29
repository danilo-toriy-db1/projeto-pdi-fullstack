package com.project.my_landing_page.http.converters;

import com.project.my_landing_page.http.request.SobreMimModelRequest;
import com.project.my_landing_page.http.response.SobreMimModelResponse;
import com.project.my_landing_page.models.SobreMimModel;
import org.springframework.stereotype.Component;

@Component
public class SobreMimModelConverter {

    public SobreMimModel toEntity(SobreMimModelRequest req) {
        SobreMimModel model = new SobreMimModel();
        model.setNome(req.nome());
        model.setIdade(req.idade());
        model.setCarreira(req.carreira());
        model.setProfissao(req.profissao());
        model.setEmpresa(req.empresa());
        return model;
    }

    public SobreMimModelResponse toResponse(SobreMimModel model) {
        return new SobreMimModelResponse(
                model.getId(),
                model.getNome(),
                model.getIdade(),
                model.getCarreira(),
                model.getProfissao(),
                model.getEmpresa()
        );
    }
}
