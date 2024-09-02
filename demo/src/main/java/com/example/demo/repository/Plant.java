package com.example.demo.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PlantType name;

    private String variety;

    private Integer minSpacing; // Inches between plants, diameter

    public Plant() {
    }

    public Plant(Long id, PlantType name, String variety, Integer minSpacing) {
        this.id = id;
        this.name = name;
        this.variety = variety;
        this.minSpacing = minSpacing;
    }
}
