package com.ppetrica.facepalm.backend.entities;


import com.ppetrica.facepalm.backend.data.Reactions;
import lombok.*;


@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Image {
    @NonNull
    private final String username;

    @NonNull
    private final String timestamp;

    @NonNull
    private final String location;

    @NonNull
    private final Reactions reactions;
}
