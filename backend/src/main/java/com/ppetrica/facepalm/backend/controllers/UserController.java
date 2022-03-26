package com.ppetrica.facepalm.backend.controllers;


import com.ppetrica.facepalm.backend.entities.User;
import com.ppetrica.facepalm.backend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@CrossOrigin(origins = "http://localhost:3000") // TODO: Update to origin when we can deploy
@RequestMapping("/api/v1/users")
public class UserController {
    @NonNull
    private final UserService userService;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable @NonNull String username) {
        return userService.getUser(username);
    }
}
