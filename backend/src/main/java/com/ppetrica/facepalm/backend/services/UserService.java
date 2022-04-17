package com.ppetrica.facepalm.backend.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ppetrica.facepalm.backend.entities.User;
import com.ppetrica.facepalm.backend.persistence.FacepalmItem;
import com.ppetrica.facepalm.backend.persistence.UserMapper;

import com.ppetrica.facepalm.backend.util.PresignedImageUrlUserDecorator;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    @NonNull
    private final DynamoDBMapper ddbMapper;

    @NonNull
    private final UserMapper userMapper;

    @NonNull
    private final PresignedImageUrlUserDecorator presignedImageUrlUserDecorator;

    public User getUser(@NonNull String username) {
        String pk = "USER#" + username;
        String sk = "#METADATA#" + username;
        
        FacepalmItem userItem = ddbMapper.load(FacepalmItem.class, pk, sk);

        User user = userMapper.facepalmItemToUser(userItem);

        log.info("Retrieved user {}", user.toString());

        java.util.Date expiration = new java.util.Date();

        // Set the presigned URL to expire after one hour.
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        return presignedImageUrlUserDecorator.changeImageLocationToPresignedUrl(user, expiration);
    }
}
