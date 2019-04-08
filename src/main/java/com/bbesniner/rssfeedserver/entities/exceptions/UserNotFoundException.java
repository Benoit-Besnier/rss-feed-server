package com.bbesniner.rssfeedserver.entities.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String identifier) {
        super("User: " + identifier + " not found.");
    }
}
