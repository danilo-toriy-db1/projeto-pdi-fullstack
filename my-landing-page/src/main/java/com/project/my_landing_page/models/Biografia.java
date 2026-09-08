package com.project.my_landing_page.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "biografia", schema = "landing_pages")
public class Biografia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String biografia;
}
