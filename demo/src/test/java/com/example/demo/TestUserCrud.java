package com.example.demo;

import com.example.demo.controller.PracticeController;
import com.example.demo.resource.UserResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DemoApplication.class)
public class TestUserCrud {

    @Autowired
    PracticeController practiceController;

    @Test
    public void testUserCrud() {

        // Create User
        UserResource expectedUser = practiceController.createUser(new UserResource(null, "test user"));
        Assertions.assertNotNull(expectedUser.getId());
        Assertions.assertEquals("test user", expectedUser.getName());

        // Read User
        UserResource userResp = practiceController.readUser(expectedUser.getId());
        assertOnUserResource(expectedUser, userResp);

        // Update User
        expectedUser.setName("test user update");
        userResp = practiceController.updateUser(expectedUser, expectedUser.getId());
        assertOnUserResource(expectedUser, userResp);

        // Delete User
        practiceController.deleteUser(expectedUser.getId());

        // Try Read Deleted User
        Assertions.assertThrows(NoSuchElementException.class, () -> {practiceController.readUser(expectedUser.getId());});
    }

    private void assertOnUserResource(UserResource expected, UserResource actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
    }
}
