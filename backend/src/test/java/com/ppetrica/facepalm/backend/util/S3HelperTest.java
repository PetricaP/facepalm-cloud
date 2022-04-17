package com.ppetrica.facepalm.backend.util;


import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static com.ppetrica.facepalm.backend.data.UserData.EXPECTED_PRESIGNED_URL;
import static com.ppetrica.facepalm.backend.data.UserData.EXPIRATION;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class S3HelperTest {
    private static final String VALID_S3_URL = "s3://quick-photos/photos/monica63/2018-08-25T14:27:07.png";
    private static final String INVALID_S3_URL = "this-is-not-an^valid -url";
    private static final String S3_BUCKET_NAME = "quick-photos";
    private static final String S3_OBJECT_KEY = "photos/monica63/2018-08-25T14:27:07.png";

    private static URL EXPECTED_PRESIGNED_URL_URI;
    static {
        try {
            EXPECTED_PRESIGNED_URL_URI = new URL(EXPECTED_PRESIGNED_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Mock
    private AmazonS3 s3Client;

    private S3Helper unit;

    @BeforeEach
    void setUp() {
        unit = new S3Helper(s3Client);
    }

    @Test
    void getPresignedUrlSuccessful() {
        Mockito.when(s3Client.generatePresignedUrl(any()))
            .thenReturn(EXPECTED_PRESIGNED_URL_URI);

        String presignedUrl = unit.getPresignedUrl(S3_BUCKET_NAME, S3_OBJECT_KEY, EXPIRATION);

        Assertions.assertEquals(EXPECTED_PRESIGNED_URL, presignedUrl);

        // GeneratePresignedUrlRequest doesn't have an equals method and we can't check it
        Mockito.verify(s3Client).generatePresignedUrl(Mockito.any());
        Mockito.verifyNoMoreInteractions(s3Client);
    }

    @Test
    void getBucketNameValidSuccessful() throws URISyntaxException {
        String bucketName = unit.getBucketName(VALID_S3_URL);

        Assertions.assertEquals(S3_BUCKET_NAME, bucketName);
    }

    @Test
    void getBucketNameInvalidExceptionThrown() {
        Assertions.assertThrows(URISyntaxException.class, () -> unit.getBucketName(INVALID_S3_URL));
    }

    @Test
    void getObjectNameValidSuccessful() throws URISyntaxException {
        String objectName = unit.getObjectName(VALID_S3_URL);

        Assertions.assertEquals(S3_OBJECT_KEY, objectName);
    }

    @Test
    void getObjectNameInvalidExceptionThrown() {
        Assertions.assertThrows(URISyntaxException.class, () -> unit.getObjectName(INVALID_S3_URL));
    }
}
