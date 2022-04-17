package com.ppetrica.facepalm.backend.services;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ppetrica.facepalm.backend.data.DDBMapperData;
import com.ppetrica.facepalm.backend.entities.User;
import com.ppetrica.facepalm.backend.persistence.FacepalmItem;
import com.ppetrica.facepalm.backend.persistence.UserMapper;

import static com.ppetrica.facepalm.backend.data.DDBMapperData.EXPECTED_SK;
import static com.ppetrica.facepalm.backend.data.DDBMapperData.EXPECTED_USER_PK;
import static com.ppetrica.facepalm.backend.data.UserData.EXPECTED_USER;
import static com.ppetrica.facepalm.backend.data.UserData.EXPIRATION;
import static com.ppetrica.facepalm.backend.data.UserData.INSTANT;
import static com.ppetrica.facepalm.backend.data.UserData.USERNAME;
import static com.ppetrica.facepalm.backend.data.UserData.USER_WITH_PRESIGNED_IMAGE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.ppetrica.facepalm.backend.util.PresignedImageUrlUserDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private DynamoDBMapper ddbMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PresignedImageUrlUserDecorator presignedImageUrlUserDecorator;

    private UserService unit;

    private static final FacepalmItem USER_ITEM = DDBMapperData.buildValidUserItem();

    @BeforeEach
    public void setUp() {
        unit = new UserService(ddbMapper, userMapper, presignedImageUrlUserDecorator);
    }

    @Test
    public void getUserBasicRequestSucceeds() {
        // https://stackoverflow.com/questions/55289157/mock-instant-now-without-using-clock-into-constructor-or-without-clock-object
        Clock spyClock = spy(Clock.class);
        MockedStatic<Clock> clockMock = mockStatic(Clock.class);
        clockMock.when(Clock::systemUTC).thenReturn(spyClock);
        when(spyClock.instant()).thenReturn(Instant.ofEpochSecond(INSTANT));

        when(ddbMapper.load(FacepalmItem.class, EXPECTED_USER_PK, EXPECTED_SK)).thenReturn(USER_ITEM);
        when(userMapper.facepalmItemToUser(USER_ITEM)).thenReturn(EXPECTED_USER);
        when(presignedImageUrlUserDecorator.changeImageLocationToPresignedUrl(EXPECTED_USER, EXPIRATION))
            .thenReturn(USER_WITH_PRESIGNED_IMAGE_URL);

        User user = unit.getUser(USERNAME);

        assertEquals(USER_WITH_PRESIGNED_IMAGE_URL, user);

        Mockito.verify(ddbMapper).load(FacepalmItem.class, EXPECTED_USER_PK, EXPECTED_SK);
        Mockito.verifyNoMoreInteractions(ddbMapper);

        Mockito.verify(userMapper).facepalmItemToUser(USER_ITEM);
        Mockito.verifyNoMoreInteractions(userMapper);

        Mockito.verify(presignedImageUrlUserDecorator).changeImageLocationToPresignedUrl(EXPECTED_USER, EXPIRATION);
        Mockito.verifyNoMoreInteractions(presignedImageUrlUserDecorator);
    }

    @Test
    public void nullUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> unit.getUser(null));
    }

    @Test
    public void nullDDBMapperThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(null, userMapper, presignedImageUrlUserDecorator));
    }

    @Test
    public void nullUserMapperThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(ddbMapper, null, presignedImageUrlUserDecorator));
    }

    @Test
    public void nullPresignedImageUrlDecoratorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(ddbMapper, userMapper, null));
    }
}
