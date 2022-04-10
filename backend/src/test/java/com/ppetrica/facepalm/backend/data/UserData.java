package com.ppetrica.facepalm.backend.data;

import java.util.List;

import com.ppetrica.facepalm.backend.entities.User;

import lombok.experimental.UtilityClass;


@UtilityClass
public class UserData {
    public final String USERNAME = "haroldwatkins";
    public final String NAME = "Harold Watkins";
    public final String ADDRESS = "Manhattan";
    public final String EMAIL = "bob@gmail.com";
    public final String BIRTHDAY = "1916-09-06";
    public final int FOLLOWERS = 5;
    public final int FOLLOWING = 10;
    public final List<String> INTERESTS = List.of("jogging", "c++", "teaching");
    public final String STATUS = "Musician";
    public final String PINNED_IMAGE = "PHOTO#haroldwatkins#2018-06-09T15:00:24";

    public final User EXPECTED_USER = User.builder()
        .username(USERNAME)
        .address(ADDRESS)
        .email(EMAIL)
        .followers(FOLLOWERS)
        .following(FOLLOWING)
        .interests(INTERESTS)
        .birthdate(BIRTHDAY)
        .name(NAME)
        .status(STATUS)
        .pinnedImage(PINNED_IMAGE)
        .build();
}
