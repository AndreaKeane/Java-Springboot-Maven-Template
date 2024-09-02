package com.example.demo;

import com.example.demo.controller.PlantController;
import com.example.demo.resource.PlantResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DemoApplication.class)
public class TestPlantCrud {

    @Autowired
    PlantController plantController;

    @Test
    public void testPlantCrud() {

        // Create Plant
        PlantResource expectedPlant = plantController.createPlant(new PlantResource(null, "CHARD", "Ruby Red", 12));
        Assertions.assertNotNull(expectedPlant.getId());
        Assertions.assertEquals("CHARD", expectedPlant.getName());

        // Read Plant
        PlantResource userResp = plantController.readPlant(expectedPlant.getId());
        assertOnUserResource(expectedPlant, userResp);

        // Update Plant
        expectedPlant.setName("SPINACH");
        userResp = plantController.updatePlant(expectedPlant, expectedPlant.getId());
        assertOnUserResource(expectedPlant, userResp);

        // Delete Plant
        plantController.deletePlant(expectedPlant.getId());

        // Try Read Deleted Plant
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            plantController.readPlant(expectedPlant.getId());
        });
    }

    private void assertOnUserResource(PlantResource expected, PlantResource actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
    }
}
