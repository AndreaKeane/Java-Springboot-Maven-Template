package com.example.demo.controller;

import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PracticeController {

    // https://spring.io/guides/tutorials/rest

    // Create
    @PostMapping("/user")
    UserResponse createUser(@RequestBody UserRequest newUser) {
        return new UserResponse();
    }

    // Read One
    @GetMapping("/user/{id}")
    UserResponse readUser(@PathVariable Long id) {
        // return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return new UserResponse();
    }

    // Read All
    @GetMapping("/users")
    List<UserResponse> readAllUsers() {
        return new ArrayList<>();
    }

    // Update
    @PutMapping("/user/{id}")
    UserResponse replaceEmployee(@RequestBody UserRequest user, @PathVariable Long id) {
        return new UserResponse();
    }

    // Delete
    @DeleteMapping("/user/{id}")
    UserResponse deleteUser(@PathVariable Long id) {
        // repository.deleteById(id);
        return new UserResponse();
    }

}
