package com.project.my_landing_page.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "desgostos", schema = "landing_pages")
public class Desgosto {

    @Id
    @OneToOne(mappedBy = "desgosto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desgosto;
}
