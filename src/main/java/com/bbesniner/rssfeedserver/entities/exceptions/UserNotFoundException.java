package com.bbesniner.rssfeedserver.entities.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final Long id) {
        super("User: " + id + " not found.");
    }

}
