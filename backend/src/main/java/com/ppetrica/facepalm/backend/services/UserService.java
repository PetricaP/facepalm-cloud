package com.ppetrica.facepalm.backend.services;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.ppetrica.facepalm.backend.entities.Image;
import com.ppetrica.facepalm.backend.entities.User;
import com.ppetrica.facepalm.backend.persistence.FacepalmItem;
import com.ppetrica.facepalm.backend.persistence.ImageMapper;
import com.ppetrica.facepalm.backend.persistence.UserMapper;

import com.ppetrica.facepalm.backend.util.ImageUtil;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.net.URISyntaxException;
import java.net.URL;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    @NonNull
    private final DynamoDBMapper ddbMapper;

    @NonNull
    private final AmazonS3 s3Client;
    
    @NonNull
    private final UserMapper userMapper;

    @NonNull
    private final ImageMapper imageMapper;


    public User getUser(@NonNull String username) {
        String pk = "USER#" + username;
        String sk = "#METADATA#" + username;
        
        FacepalmItem userItem = ddbMapper.load(FacepalmItem.class, pk, sk);

        User user = userMapper.facepalmItemToUser(userItem);

        log.info("Retrieved user {}", user.toString());

        String pinnedImageSk = user.getPinnedImage();

        FacepalmItem pinnedImageItem = ddbMapper.load(FacepalmItem.class, pk, pinnedImageSk);

        Image pinnedImage = imageMapper.facepalmItemToImage(pinnedImageItem);

        log.info("Retrieved pinned image {}", pinnedImage.toString());

        String imageLocation = pinnedImage.getLocation();

        String bucketName;
        String objectName;

        try {
            bucketName = ImageUtil.getBucketName(imageLocation);
            objectName = ImageUtil.getObjectName(imageLocation);
        } catch (URISyntaxException ex) {
            log.error("Invalid image URL {}: {}", imageLocation, ex);

            throw new RuntimeException(ex);
        }

        try {
            URL presignedImageUrl = ImageUtil.getPresignedUrl(s3Client, bucketName, objectName);

            user.setPinnedImage(presignedImageUrl.toString());

            log.info("Presigned pinned image url: {}", presignedImageUrl);

            return user;
        } catch (SdkClientException ex) {
            log.error("Failed to generate presigned url for bucket {} and object {}: {}", bucketName, objectName, ex);

            throw new RuntimeException(ex);
        }
    }
}
