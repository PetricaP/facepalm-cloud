package com.ppetrica.facepalm.backend.data;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;


@Getter
@AllArgsConstructor
public class Reactions {
    @Min(value = 0L, message = "The value must be positive")
    private final int likes;

    @Min(value = 0L, message = "The value must be positive")
    private final int smileys;

    @Min(value = 0L, message = "The value must be positive")
    private final int sunglasses;

    @Min(value = 0L, message = "The value must be positive")
    private final int hearts;
}
