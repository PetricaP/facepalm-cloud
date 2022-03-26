package com.ppetrica.facepalm.backend.services;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ppetrica.facepalm.backend.entities.User;
import com.ppetrica.facepalm.backend.persistence.FacepalmItem;
import com.ppetrica.facepalm.backend.persistence.UserMapper;
import static com.ppetrica.facepalm.backend.data.UserData.*;
import static com.ppetrica.facepalm.backend.data.DDBMapperData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    private UserService unit;

    private static final FacepalmItem USER_ITEM = buildValidUserItem();

    @BeforeEach
    public void setUp() {
        unit = new UserService(ddbMapper, userMapper);
    }

    @Test
    public void getUserBasicRequestSucceeds() {
        Mockito.when(ddbMapper.load(FacepalmItem.class, EXPECTED_PK, EXPECTED_SK)).thenReturn(USER_ITEM);
        Mockito.when(userMapper.facepalmItemToUser(USER_ITEM)).thenReturn(EXPECTED_USER);

        User user = unit.getUser(USERNAME);

        assertEquals(EXPECTED_USER, user);

        Mockito.verify(ddbMapper).load(FacepalmItem.class, EXPECTED_PK, EXPECTED_SK);
        Mockito.verifyNoMoreInteractions(ddbMapper);

        Mockito.verify(userMapper).facepalmItemToUser(USER_ITEM);
        Mockito.verifyNoMoreInteractions(userMapper);
    }

    @Test
    public void nullUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> unit.getUser(null));
    }

    @Test
    public void nullDDBMapperThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(null, userMapper));
    }

    @Test
    public void nullUserMapperThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(ddbMapper, null));
    }
}
