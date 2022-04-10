package com.ppetrica.facepalm.backend.persistence;

import javax.validation.Valid;

import com.ppetrica.facepalm.backend.entities.User;
import org.mapstruct.Mapper;

import lombok.NonNull;


@Mapper
public class UserMapper {
    @Valid
    public User facepalmItemToUser(@NonNull FacepalmItem item) {
        return User.builder()
            .username(item.getUsername())
            .name(item.getName())
            .address(item.getAddress())
            .email(item.getEmail())
            .status(item.getStatus())
            .birthdate(item.getBirthdate())
            .interests(item.getInterests())
            .followers(item.getFollowers())
            .following(item.getFollowing())
            .pinnedImage(item.getPinnedImage())
            .build();
    }
}
