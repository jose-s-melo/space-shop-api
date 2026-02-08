package com.melo.space_shop_api.exception;

/**
 * Exception thrown when a requested product is not found in the system.
 * 
 * This is a runtime exception that indicates a product lookup operation
 * failed because the specified product does not exist in the database
 * or repository.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Product not found");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
