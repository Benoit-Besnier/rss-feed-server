package com.bbesniner.rssfeedserver.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    public InvalidJwtAuthenticationException(final String message) {
        super(message);
    }

}
