package com.project.my_landing_page.DTOs;

import com.project.my_landing_page.models.SobreMimModel;

public record SobreMimModelDTO(Long id, String nome, int idade, String carreira, String profissao, String empresa) {
    public static SobreMimModelDTO fromEntity(SobreMimModel sobreMimModel) {
        return new SobreMimModelDTO(
                sobreMimModel.getId(),
                sobreMimModel.getNome(),
                sobreMimModel.getIdade(),
                sobreMimModel.getCarreira(),
                sobreMimModel.getProfissao(),
                sobreMimModel.getEmpresa()
        );
    }

    public static SobreMimModel toEntity(SobreMimModelDTO sobreMimModelDTO){
        return new SobreMimModel(
                sobreMimModelDTO.id() != null ? sobreMimModelDTO.id() : 0L,
                sobreMimModelDTO.nome(),
                sobreMimModelDTO.idade(),
                sobreMimModelDTO.carreira(),
                sobreMimModelDTO.profissao(),
                sobreMimModelDTO.empresa()
        );
    }
}
