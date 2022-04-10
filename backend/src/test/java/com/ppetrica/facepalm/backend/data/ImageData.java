package com.ppetrica.facepalm.backend.data;

import java.net.MalformedURLException;
import java.net.URL;

import com.ppetrica.facepalm.backend.entities.Image;

import lombok.experimental.UtilityClass;


@UtilityClass
public class ImageData {
    public final String USERNAME = "haroldwatkins";
    public final String TIMESTAMP = "2018-06-09T15:00:24";
    public final String S3_BUCKET = "quick-photos";
    public final String S3_OBJECT = "photos/haroldwatkins/2018-06-09T15:00:24.png";
    public final String LOCATION = "s3://" + S3_BUCKET + "/" + S3_OBJECT;
    
    public final URL EXPECTED_PRESIGNED_URL;
    static {
        try {
            EXPECTED_PRESIGNED_URL = new URL("https://my-url.com");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public final Reactions REACTIONS = new Reactions(1, 0, 0, 0);

    public final Image EXPECTED_IMAGE = Image.builder()
        .username(USERNAME)
        .timestamp(TIMESTAMP)
        .location(LOCATION)
        .reactions(REACTIONS)
        .build();
}
