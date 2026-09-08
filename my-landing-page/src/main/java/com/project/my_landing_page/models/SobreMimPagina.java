package com.project.my_landing_page.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sobre_mim_pagina", schema = "landing_pages")
public class SobreMimPagina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Usuario usuarioId;

    @OneToOne
    private SobreMimModel sobreMimModel;

    @OneToOne
    private Biografia biografia;

    @OneToOne
    private Desgosto desgosto;

    @OneToOne
    private Hobbie hobbie;

    @OneToOne
    private Objetivo objetivo;
}
