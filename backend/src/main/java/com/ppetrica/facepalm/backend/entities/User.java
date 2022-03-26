package com.ppetrica.facepalm.backend.entities;

import java.util.List;

import javax.validation.constraints.Min;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


@Builder
@Getter
@EqualsAndHashCode
@ToString
public class User {
    @NonNull
    private final String address;

    @NonNull
    private final String email;

    @NonNull
    private final String username;

    @NonNull
    private final String status;

    @NonNull
    private final List<String> interests;

    @Min(value = 0L, message = "The value must be positive")
    private final int followers;
    
    @Min(value = 0L, message = "The value must be positive")
    private final int following;

    @NonNull
    private final String birthdate;

    @NonNull
    private final String name;

    @NonNull
    private final String pinnedImage;
}
