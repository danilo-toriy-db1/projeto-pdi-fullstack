package com.project.my_landing_page.models;


import com.project.my_landing_page.models.enums.UsersRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "usuario", schema = "auth")
public class Usuario {

    @Id
    @OneToOne(mappedBy = "usuarioId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String usuario;
    private String senha;
    private Timestamp data_cadastro;
    private UsersRoles role;
}
