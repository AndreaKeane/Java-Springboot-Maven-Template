package com.example.garden.service;

import com.example.garden.repository.Plant;
import com.example.garden.repository.PlantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PlantService {

    Logger log = LoggerFactory.getLogger(PlantService.class);

    @Autowired
    PlantRepository plantRepository;

    @Cacheable(value = "PlantCache", unless = "#result == null")
    public Plant getPlant(Long plantId) {
        log.info("Looking up plant by Id: {}", plantId);
        return plantRepository.findById(plantId).orElseThrow(NoSuchElementException::new);
    }
}
