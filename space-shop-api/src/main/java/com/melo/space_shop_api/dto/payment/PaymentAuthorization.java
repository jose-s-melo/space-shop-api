package com.melo.space_shop_api.dto.payment;

import com.melo.space_shop_api.entity.payment.PaymentMethod;

public record PaymentAuthorization(
    Boolean auth,
    PaymentMethod method
) {}
