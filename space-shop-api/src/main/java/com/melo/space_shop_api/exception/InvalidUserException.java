package com.melo.space_shop_api.exception;

public class InvalidUserException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Invalid user field";

    public InvalidUserException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
