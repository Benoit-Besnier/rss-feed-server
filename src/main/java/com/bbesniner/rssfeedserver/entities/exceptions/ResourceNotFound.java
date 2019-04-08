package com.bbesniner.rssfeedserver.entities.exceptions;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(final String identifier, final Class clazz) {
        super(clazz + " of " + identifier + " not found.");
    }

}
