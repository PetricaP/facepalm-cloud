package com.ppetrica.facepalm.backend.persistence;

import com.ppetrica.facepalm.backend.entities.User;
import org.mapstruct.Mapper;

import lombok.NonNull;


@Mapper
public class UserMapper {
    public User facepalmItemToUser(@NonNull FacepalmItem item) {
        return User.builder()
            .username(item.getUsername())
            .address(item.getAddress())
            .email(item.getEmail())
            .status(item.getStatus())
            .interests(item.getInterests())
            .followers(item.getFollowers())
            .following(item.getFollowing())
            .build();
    }
}
