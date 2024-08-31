package com.example.demo.controller;

import com.example.demo.repository.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.resource.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PracticeController {

    Logger log = LoggerFactory.getLogger(PracticeController.class);

    @Autowired
    private UserRepository userRepository;

    // Create
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResource createUser(@RequestBody UserResource newUser) {
        log.info("Creating User: {}", newUser);
        User user = newUser.toUser();
        userRepository.save(user);
        return new UserResource(user);
    }

    // Read One
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource readUser(@PathVariable Long id) {
        log.info("Reading User by Id: {}", id);
        User user = userRepository.findById(id).orElseThrow();
        return new UserResource(user);
    }

    // Read All
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResource> readAllUsers() {
        log.info("Reading All Users");
        return userRepository.findAll().stream().map(UserResource::new).collect(Collectors.toList());
    }

    // Update
    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource updateUser(@RequestBody UserResource userReq, @PathVariable Long id) {
        log.info("Updating User by Id <{}> with values <{}>", id, userReq);
        User updatedUser = userRepository.findById(id).map(
                user -> {
                    user.setName(userReq.getName());
                    return userRepository.save(user);
                }
        ).orElseGet(() -> {
            return userRepository.save(userReq.toUser());
        });
        return new UserResource(updatedUser);
    }

    // Delete
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        log.info("Deleting User by Id: {}", id);
        userRepository.deleteById(id);
    }

}
