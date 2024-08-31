package com.example.demo.resource;

import com.example.demo.repository.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResource {

    private final Long id;
    private String name;

    public UserResource(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserResource(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public User toUser() {
        return new User(this.id, this.name);
    }
}
