package com.project.my_landing_page.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "objetivos", schema = "landing_pages")
public class Objetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String objetivo;
}
