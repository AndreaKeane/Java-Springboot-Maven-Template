package com.example.garden.controller;

import com.example.garden.repository.Plant;
import com.example.garden.repository.PlantRepository;
import com.example.garden.repository.PlantType;
import com.example.garden.resource.PlantResource;
import com.example.garden.service.PlantService;
import com.github.benmanes.caffeine.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class PlantController {

    Logger log = LoggerFactory.getLogger(PlantController.class);

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private PlantService plantService;

    @Autowired
    private CacheManager cacheManager;

    // Create
    @PostMapping("/plant")
    @ResponseStatus(HttpStatus.CREATED)
    public PlantResource createPlant(@RequestBody PlantResource newPlant) {
        log.info("Creating Plant: {}", newPlant);
        Plant plant = newPlant.toPlant();
        plantRepository.save(plant);
        return new PlantResource(plant);
    }

    // Read One
    @GetMapping("/plant/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlantResource readPlant(@PathVariable Long id) {
        log.info("Reading Plant by Id: {}", id);
        Plant plant = plantService.getPlant(id);
        return new PlantResource(plant);
    }

    // Read All
    @GetMapping("/plant")
    @ResponseStatus(HttpStatus.OK)
    public List<PlantResource> readAllPlants() {
        log.info("Reading All Plants");
        return plantRepository.findAll().stream().map(PlantResource::new).collect(Collectors.toList());
    }

    // Update
    @PutMapping("/plant/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlantResource updatePlant(@RequestBody PlantResource plantReq, @PathVariable Long id) {
        log.info("Updating Plant by Id <{}> with values <{}>", id, plantReq);
        Plant updatedPlant = plantRepository.findById(id).map(
                plant -> {
                    plant.setName(PlantType.fromString(plantReq.getName()));
                    return plantRepository.save(plant);
                }
        ).orElseGet(() -> {
            return plantRepository.save(plantReq.toPlant());
        });
        return new PlantResource(updatedPlant);
    }

    // Delete
    @DeleteMapping("/plant/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlant(@PathVariable Long id) {
        log.info("Deleting Plant by Id: {}", id);

        // Delete Plant from our cache
        CaffeineCache plantCache = (CaffeineCache) cacheManager.getCache("PlantCache");
        if (plantCache != null) {
            plantCache.evictIfPresent(id);
        }

        // Then delete from the repository
        plantRepository.deleteById(id);
    }

    @GetMapping(value = "/inspectCache")
    public void inspectCache(String cacheName) {
        CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache(cacheName);
        Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
        for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
            System.out.println("Key = " + entry.getKey());
            System.out.println("Value = " + entry.getValue());
        }
    }

}
