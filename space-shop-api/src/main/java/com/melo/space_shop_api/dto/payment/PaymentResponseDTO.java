package com.melo.space_shop_api.dto.payment;

import java.math.BigDecimal;

import com.melo.space_shop_api.entity.payment.PaymentMethod;
import com.melo.space_shop_api.entity.payment.PaymentStatus;

public record PaymentResponseDTO(
    Long id,
    BigDecimal value,
    PaymentStatus status,
    PaymentMethod method,
    Long userId
) {}
