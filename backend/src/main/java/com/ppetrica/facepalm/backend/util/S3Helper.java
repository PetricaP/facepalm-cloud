package com.ppetrica.facepalm.backend.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;


@Log4j2
@RequiredArgsConstructor
@Component
public class S3Helper {
    @NonNull
    AmazonS3 s3Client;

    public String getPresignedUrl(@NonNull String bucketName, @NonNull String keyName, Date expiration) {
        log.info("Generating pre-signed URL.");

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucketName, keyName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    public String getBucketName(String s3ObjectUrl) throws URISyntaxException {
        URI uri = new URI(s3ObjectUrl);

        return uri.getHost();
    }

    public String getObjectName(String s3ObjectUrl) throws URISyntaxException {
        URI uri = new URI(s3ObjectUrl);

        return uri.getPath().substring(1);
    }
}
