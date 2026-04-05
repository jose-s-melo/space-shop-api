package com.melo.space_shop_api.dto.payment;

import java.io.Serializable;

import com.melo.space_shop_api.entity.payment.PaymentMethod;

public record PaymentAuthorizeResponseDTO(
    String message,
    boolean authorize,
    PaymentMethod method
) implements Serializable {}
