package com.ppetrica.facepalm.backend.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;


@Log4j2
@UtilityClass
public class ImageUtil {
    public URL getPresignedUrl(AmazonS3 s3Client, String bucketName, String keyName) throws SdkClientException {
        // Set the presigned URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        log.info("Generating pre-signed URL.");
        
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucketName, keyName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
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
