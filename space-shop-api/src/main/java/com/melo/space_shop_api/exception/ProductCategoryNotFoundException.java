package com.melo.space_shop_api.exception;

public class ProductCategoryNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "ProductCategory not found";

    public ProductCategoryNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ProductCategoryNotFoundException(String message) {
        super(message);
    }
}
