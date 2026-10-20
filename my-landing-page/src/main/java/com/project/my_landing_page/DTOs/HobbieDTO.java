package com.project.my_landing_page.DTOs;

import com.project.my_landing_page.models.Hobbie;

public record HobbieDTO(Long id, String hobbie) {
    public static HobbieDTO fromEntity(Hobbie hobbie) {
        return new HobbieDTO(
                hobbie.getId(),
                hobbie.getHobbie()
        );
    }

    public static Hobbie toEntity(HobbieDTO hobbieDTO){
        return new Hobbie(
                hobbieDTO.id() != null ? hobbieDTO.id() : 0L,
                hobbieDTO.hobbie()
        );
    }
}
