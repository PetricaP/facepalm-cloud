package com.ppetrica.facepalm.backend.util;


import com.amazonaws.SdkClientException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ppetrica.facepalm.backend.entities.Image;
import com.ppetrica.facepalm.backend.entities.User;
import com.ppetrica.facepalm.backend.persistence.FacepalmItem;
import com.ppetrica.facepalm.backend.persistence.ImageMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.Date;


@Log4j2
@Component
@RequiredArgsConstructor
public class PresignedImageUrlUserDecorator {
    @NonNull
    private final DynamoDBMapper ddbMapper;

    @NonNull
    private final ImageMapper imageMapper;

    @NonNull
    private final S3Helper s3Helper;

    public User changeImageLocationToPresignedUrl(User user, Date expiration) {
        String userPrimaryKey = "USER#" + user.getUsername();
        String pinnedImageSk = user.getPinnedImage();

        FacepalmItem pinnedImageItem = ddbMapper.load(FacepalmItem.class, userPrimaryKey, pinnedImageSk);

        Image pinnedImage = imageMapper.facepalmItemToImage(pinnedImageItem);

        log.info("Retrieved pinned image {}", pinnedImage.toString());

        String imageLocation = pinnedImage.getLocation();

        try {
            String presignedImageUrl = getPresignedUrl(imageLocation, expiration);

            log.info("Presigned pinned image url: {}", presignedImageUrl);

            return user.toBuilder().pinnedImage(presignedImageUrl).build();
        } catch (SdkClientException ex) {
            log.error("Failed to generate presigned url for image location {}: {}", imageLocation, ex);

            throw new RuntimeException(ex);
        }
    }

    private String getPresignedUrl(String imageLocation, Date expiration) {
        String bucketName;
        String objectName;

        try {
            bucketName = s3Helper.getBucketName(imageLocation);
            objectName = s3Helper.getObjectName(imageLocation);
        } catch (URISyntaxException ex) {
            log.error("Invalid image URL {}: {}", imageLocation, ex);

            throw new RuntimeException(ex);
        }

        return s3Helper.getPresignedUrl(bucketName, objectName, expiration);
    }
}
