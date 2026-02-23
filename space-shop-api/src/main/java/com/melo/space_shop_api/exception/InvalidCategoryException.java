package com.melo.space_shop_api.exception;

public class InvalidCategoryException extends IllegalArgumentException {

    public static final String DEFAULT_MESSAGE = "Invalid category field";

    public InvalidCategoryException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCategoryException(String message) {
        super(message);
    }
}
