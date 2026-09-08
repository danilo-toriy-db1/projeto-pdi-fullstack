package com.project.my_landing_page.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hobbies", schema = "landing_pages")
public class Hobbie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String hobbie;
}
