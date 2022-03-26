package com.ppetrica.facepalm.backend.persistence;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.ppetrica.facepalm.backend.data.DDBMapperData.*;
import static com.ppetrica.facepalm.backend.data.UserData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ppetrica.facepalm.backend.entities.User;


public class UserMapperTest {
    private UserMapper unit;    

    private static final FacepalmItem USER_ITEM = buildValidUserItem();

    @BeforeEach
    public void setUp() {
        unit = new UserMapper();
    }

    @Test
    public void testValidUserMapping() {
        User user = unit.facepalmItemToUser(USER_ITEM);
        
        assertEquals(EXPECTED_USER, user);
    }

    @ParameterizedTest
    @MethodSource("com.ppetrica.facepalm.backend.data.DDBMapperData#provideInvalidUserItemsWithNullFields")
    public void testInvalidUserMappingWithNullFields(FacepalmItem item) {
        assertThrows(IllegalArgumentException.class, () -> unit.facepalmItemToUser(item));
    }

    @Test
    public void testInvalidUserNullMapping() {
        assertThrows(IllegalArgumentException.class, () -> unit.facepalmItemToUser(null));
    }

    // TODO: This test is failing, validation annotations don't seem to work.
    @Disabled
    @ParameterizedTest
    @MethodSource("com.ppetrica.facepalm.backend.data.DDBMapperData#provideInvalidUserItemsWithInvalidFollowers")
    public void testInvalidUserMappingWithInvalidFollowers(FacepalmItem item) {
        assertThrows(NullPointerException.class, () -> unit.facepalmItemToUser(item));
    }
}
