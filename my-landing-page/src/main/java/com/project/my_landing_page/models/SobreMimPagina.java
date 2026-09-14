package com.project.my_landing_page.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sobre_mim_pagina", schema = "landing_pages")
public class SobreMimPagina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioId;

    @OneToOne
    @JoinColumn(name = "sobre_mim_id")
    private SobreMimModel sobreMimModel;

    @OneToOne
    @JoinColumn(name = "biografia_id")
    private Biografia biografia;

    @OneToOne
    @JoinColumn(name = "desgosto_id")
    private Desgosto desgosto;

    @OneToOne
    @JoinColumn(name = "hobbie_id")
    private Hobbie hobbie;

    @OneToOne
    @JoinColumn(name = "objetivo_id")
    private Objetivo objetivo;
}
