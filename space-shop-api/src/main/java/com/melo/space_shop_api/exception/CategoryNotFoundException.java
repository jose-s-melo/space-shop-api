package com.melo.space_shop_api.exception;

public class CategoryNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Category not found"; 

    public CategoryNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
