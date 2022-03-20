package com.ppetrica.facepalm.backend.controllers;

import java.util.Map;
import java.util.UUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.ppetrica.facepalm.backend.config.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("users")
public class UsersController {
    private final Properties properties;
    private final AmazonDynamoDB ddbClient;
    private final ObjectMapper objectMapper;

	@GetMapping("/create")
    public String createUser(@RequestParam String name) {
        Map<String, AttributeValue> user = ImmutableMap.of(
            "id", new AttributeValue(UUID.randomUUID().toString()),
            "name", new AttributeValue(name)
        );

        try {
            ddbClient.putItem(properties.getUsersTableName(), user);
        } catch (AmazonServiceException e) {
            log.error(e.getErrorMessage());
            return e.getErrorMessage();
        }

        return String.format("Hello %s!", name);
    }

    @GetMapping("/get")
    public String getUser(@RequestParam String id) {
        GetItemRequest request = new GetItemRequest()
            .withTableName(properties.getUsersTableName())
            .withKey(ImmutableMap.of("id", new AttributeValue(id)));

        try {
            Map<String, AttributeValue> item = ddbClient.getItem(request).getItem();

            log.info("Fetched user with id {} and name {}", item.get("id").getS(), item.get("name").getS());
            
            return objectMapper.writeValueAsString(item);
        } catch (AmazonServiceException | JsonProcessingException e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }
}
