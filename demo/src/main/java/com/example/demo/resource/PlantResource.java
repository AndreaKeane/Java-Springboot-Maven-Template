package com.example.demo.resource;

import com.example.demo.repository.Plant;
import com.example.demo.repository.PlantType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantResource {

    private final Long id;
    private String name;
    private String variety;
    private Integer minSpacing; // Inches between plants, diameter

    public PlantResource(Long id, String name, String variety, Integer minSpacing) {
        this.id = id;
        this.name = name;
        this.variety = variety;
        this.minSpacing = minSpacing;
    }

    public PlantResource(Plant plant) {
        this.id = plant.getId();
        this.name = plant.getName().toString();
        this.variety = plant.getVariety();
        this.minSpacing = plant.getMinSpacing();
    }

    public Plant toPlant() {
        return new Plant(this.id, PlantType.fromString(this.name), this.variety, this.minSpacing);
    }
}
