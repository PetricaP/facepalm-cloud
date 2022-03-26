package com.ppetrica.facepalm.backend.controllers;

import com.ppetrica.facepalm.backend.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.ppetrica.facepalm.backend.data.UserData.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ppetrica.facepalm.backend.entities.User;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    UserService userService;

    UserController unit;

    @BeforeEach
    public void setUp() {
        unit = new UserController(userService);
    }

    @Test
    public void getUserTest() {
        Mockito.when(userService.getUser(USERNAME)).thenReturn(EXPECTED_USER);

        User user = unit.getUser(USERNAME);

        assertSame(EXPECTED_USER, user);

        Mockito.verify(userService).getUser(USERNAME);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void nullUserServiceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UserController(null));
    }
}
