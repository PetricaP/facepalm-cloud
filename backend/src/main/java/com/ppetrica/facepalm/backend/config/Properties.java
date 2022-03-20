package com.ppetrica.facepalm.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;


@ConfigurationProperties("backend")
@Getter
@Component
public class Properties {
    private final String usersTableName = "users";
}
