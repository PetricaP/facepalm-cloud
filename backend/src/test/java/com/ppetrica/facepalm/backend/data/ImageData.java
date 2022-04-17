package com.ppetrica.facepalm.backend.data;

import java.net.MalformedURLException;
import java.net.URL;

import com.ppetrica.facepalm.backend.entities.Image;

import lombok.experimental.UtilityClass;


@UtilityClass
public class ImageData {
    public static final String USERNAME = "haroldwatkins";
    public static final String TIMESTAMP = "2018-06-09T15:00:24";
    public static final String S3_BUCKET = "quick-photos";
    public static final String S3_OBJECT = "photos/haroldwatkins/2018-06-09T15:00:24.png";
    public static final String LOCATION = "s3://" + S3_BUCKET + "/" + S3_OBJECT;

    public static final Reactions REACTIONS = new Reactions(1, 0, 0, 0);

    public static final Image EXPECTED_IMAGE = Image.builder()
        .username(USERNAME)
        .timestamp(TIMESTAMP)
        .location(LOCATION)
        .reactions(REACTIONS)
        .build();
}
