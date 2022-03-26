package com.ppetrica.facepalm.backend.controllers;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ppetrica.facepalm.backend.config.Properties;
import com.ppetrica.facepalm.backend.entities.User;
import com.ppetrica.facepalm.backend.persistence.FacepalmItem;
import com.ppetrica.facepalm.backend.persistence.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@CrossOrigin(origins = "http://localhost:3000") // TODO: Update to origin when we can deploy
@RequestMapping("/api/v1/users")
public class UsersController {
    @NonNull
    private final Properties properties;
    
    @NonNull
    private final DynamoDBMapper ddbMapper;
    
    @NonNull
    private final UserMapper userMapper;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable @NonNull String username) {
        String pk = "USER#" + username;
        String sk = "#METADATA#" + username;
        
        FacepalmItem userItem = ddbMapper.load(FacepalmItem.class, pk, sk);

        User user = userMapper.facepalmItemToUser(userItem);

        log.info("Retrieved user {}", user);
        
        return user;
    }
}
