package com.melo.space_shop_api.dto.order;

import java.io.Serializable;
import java.time.Instant;

import com.melo.space_shop_api.entity.order.OrderStatus;
import com.melo.space_shop_api.entity.payment.PaymentStatus;

public record OrderDetailResponseDTO(
    Long orderId,
    Long user,
    OrderStatus orderStatus,
    PaymentStatus paymentStatus,
    Instant createdAt,
    Instant updatedAt,
    Instant paidAt,
    Instant shippedAt,
    Instant deliveredAt
) implements Serializable {}
