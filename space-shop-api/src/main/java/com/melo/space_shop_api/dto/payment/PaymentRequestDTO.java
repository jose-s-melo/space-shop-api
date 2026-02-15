package com.melo.space_shop_api.dto.payment;

import java.math.BigDecimal;

public record PaymentRequestDTO(
    BigDecimal value,
    Long userId
) {}
