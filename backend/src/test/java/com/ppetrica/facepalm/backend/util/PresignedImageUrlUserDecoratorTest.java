package com.ppetrica.facepalm.backend.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ppetrica.facepalm.backend.data.DDBMapperData;
import com.ppetrica.facepalm.backend.entities.User;
import com.ppetrica.facepalm.backend.persistence.FacepalmItem;
import com.ppetrica.facepalm.backend.persistence.ImageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;

import static com.ppetrica.facepalm.backend.data.DDBMapperData.EXPECTED_PHOTO_SK;
import static com.ppetrica.facepalm.backend.data.DDBMapperData.EXPECTED_USER_PK;
import static com.ppetrica.facepalm.backend.data.UserData.EXPECTED_PRESIGNED_URL;
import static com.ppetrica.facepalm.backend.data.UserData.EXPECTED_USER;
import static com.ppetrica.facepalm.backend.data.UserData.EXPIRATION;
import static com.ppetrica.facepalm.backend.data.UserData.USER_WITH_PRESIGNED_IMAGE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class PresignedImageUrlUserDecoratorTest {
    private PresignedImageUrlUserDecorator unit;

    @Mock
    private DynamoDBMapper ddbMapper;

    @Mock
    private ImageMapper imageMapper;

    @Mock
    private S3Helper s3Helper;

    private static final FacepalmItem IMAGE_ITEM = DDBMapperData.builValidImageItem();


    @BeforeEach
    public void setUp() {
        unit = new PresignedImageUrlUserDecorator(ddbMapper, imageMapper, s3Helper);
    }

    @Test
    void basicUserDecorateSuccessful() {
        Mockito.when(imageMapper.facepalmItemToImage(IMAGE_ITEM))
            .thenReturn(com.ppetrica.facepalm.backend.data.ImageData.EXPECTED_IMAGE);

        Mockito.when(s3Helper.getPresignedUrl(any(), any(), any())).thenReturn(
            EXPECTED_PRESIGNED_URL
        );

        Mockito.when(ddbMapper.load(FacepalmItem.class, EXPECTED_USER_PK, EXPECTED_PHOTO_SK)).thenReturn(IMAGE_ITEM);

        User user = unit.changeImageLocationToPresignedUrl(EXPECTED_USER, EXPIRATION);

        assertEquals(USER_WITH_PRESIGNED_IMAGE_URL, user);

        Mockito.verify(ddbMapper).load(FacepalmItem.class, EXPECTED_USER_PK, EXPECTED_PHOTO_SK);
        Mockito.verifyNoMoreInteractions(ddbMapper);

        Mockito.verify(imageMapper).facepalmItemToImage(IMAGE_ITEM);
        Mockito.verifyNoMoreInteractions(imageMapper);
    }
}
