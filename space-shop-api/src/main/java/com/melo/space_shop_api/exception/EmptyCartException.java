package com.melo.space_shop_api.exception;

public class EmptyCartException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Cart is empty";

    public EmptyCartException() {
        super(DEFAULT_MESSAGE);
    }

    public EmptyCartException(String message) {
        super(message);
    }

}
