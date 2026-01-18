package com.melo.space_shop_api.exception;

/**
 * Exception thrown when a product is invalid or does not meet required
 * criteria.
 * 
 * This exception extends {@link IllegalArgumentException} and is used to
 * indicate
 * that an illegal argument related to product validation has been provided.
 * 
 */
public class InvalidProductException extends IllegalArgumentException {

    public InvalidProductException(String message) {
        super(message);
    }
}
