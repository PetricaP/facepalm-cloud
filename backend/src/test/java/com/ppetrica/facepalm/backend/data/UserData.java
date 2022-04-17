package com.ppetrica.facepalm.backend.data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ppetrica.facepalm.backend.entities.User;

import lombok.experimental.UtilityClass;


@UtilityClass
public class UserData {
    public static final String USERNAME = "haroldwatkins";
    public static final String NAME = "Harold Watkins";
    public static final String ADDRESS = "Manhattan";
    public static final String EMAIL = "bob@gmail.com";
    public static final String BIRTHDAY = "1916-09-06";
    public static final int FOLLOWERS = 5;
    public static final int FOLLOWING = 10;
    public static final List<String> INTERESTS = List.of("jogging", "c++", "teaching");
    public static final String STATUS = "Musician";
    public static final String PINNED_IMAGE = "PHOTO#haroldwatkins#2018-06-09T15:00:24";
    public static final String EXPECTED_PRESIGNED_URL = "https://quick-photos.s3.eu-west-1.amazonaws.com/photos/haroldwatkins/2018-06-09T15%3A00%3A24.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&amp;X-Amz-Date=20220416T161133Z&amp;X-Amz-SignedHeaders=host&amp;X-Amz-Expires=3599&amp;X-Amz-Credential=AKIAXA4PESWKUGGUSYOP%2F20220416%2Feu-west-1%2Fs3%2Faws4_request&amp;X-Amz-Signature=991b1f9322664338977ede733fe087481f942c77452e0e0b48e7b7b73dea4e88";

    public static final User EXPECTED_USER = User.builder()
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

    public static final long INSTANT = 1640000000;

    // Expire after 1 hour
    public static final Date EXPIRATION = new Date((INSTANT + 3600) * 1000);

    public static final User USER_WITH_PRESIGNED_IMAGE_URL = Optional.of(EXPECTED_USER)
        .map((user) -> user.toBuilder().pinnedImage(EXPECTED_PRESIGNED_URL).build())
        .get();
}
