package com.ppetrica.facepalm.backend.services;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3Client;
import com.ppetrica.facepalm.backend.entities.User;
import com.ppetrica.facepalm.backend.persistence.FacepalmItem;
import com.ppetrica.facepalm.backend.persistence.ImageMapper;
import com.ppetrica.facepalm.backend.persistence.UserMapper;

import static com.ppetrica.facepalm.backend.data.UserData.*;
import static com.ppetrica.facepalm.backend.data.DDBMapperData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private DynamoDBMapper ddbMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ImageMapper imageMapper;

    @Mock
    private AmazonS3Client s3Client;

    private UserService unit;

    private static final FacepalmItem USER_ITEM = buildValidUserItem();
    private static final FacepalmItem IMAGE_ITEM = builValidImageItem();

    @BeforeEach
    public void setUp() {
        unit = new UserService(ddbMapper, s3Client, userMapper, imageMapper);
    }

    @Test
    public void getUserBasicRequestSucceeds() {
        Mockito.when(ddbMapper.load(FacepalmItem.class, EXPECTED_PK, EXPECTED_SK)).thenReturn(USER_ITEM);
        Mockito.when(userMapper.facepalmItemToUser(USER_ITEM)).thenReturn(EXPECTED_USER);

        Mockito.when(ddbMapper.load(FacepalmItem.class, EXPECTED_PK, EXPECTED_PHOTO_SK)).thenReturn(IMAGE_ITEM);
        Mockito.when(imageMapper.facepalmItemToImage(IMAGE_ITEM))
            .thenReturn(com.ppetrica.facepalm.backend.data.ImageData.EXPECTED_IMAGE);

        Mockito.when(s3Client.generatePresignedUrl(any())).thenReturn(
            com.ppetrica.facepalm.backend.data.ImageData.EXPECTED_PRESIGNED_URL
        );

        User user = unit.getUser(USERNAME);

        assertEquals(EXPECTED_USER, user);

        Mockito.verify(ddbMapper).load(FacepalmItem.class, EXPECTED_PK, EXPECTED_SK);
        Mockito.verify(ddbMapper).load(FacepalmItem.class, EXPECTED_PK, EXPECTED_PHOTO_SK);

        Mockito.verifyNoMoreInteractions(ddbMapper);

        Mockito.verify(userMapper).facepalmItemToUser(USER_ITEM);
        Mockito.verifyNoMoreInteractions(userMapper);

        Mockito.verify(imageMapper).facepalmItemToImage(IMAGE_ITEM);
        Mockito.verifyNoMoreInteractions(imageMapper);
    }

    @Test
    public void nullUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> unit.getUser(null));
    }

    @Test
    public void nullDDBMapperThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(null, s3Client, userMapper, imageMapper));
    }

    @Test
    public void nullS3ClientThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(ddbMapper, null, userMapper, imageMapper));
    }

    @Test
    public void nullUserMapperThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(ddbMapper, s3Client, null, imageMapper));
    }

    @Test
    public void nullImageMapperThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(ddbMapper, s3Client, userMapper, null));
    }
}
