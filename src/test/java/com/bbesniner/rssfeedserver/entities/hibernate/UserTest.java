package com.bbesniner.rssfeedserver.entities.hibernate;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void shouldInstantiate() {
        final String username = "Jacquie";
        final String password = "Michel";
        final User user = User.builder().username(username).password(password).build();

        user.setRoles(List.of("ROLE_USER"));
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals("ROLE_USER", user.getRoles().get(0));
    }

}