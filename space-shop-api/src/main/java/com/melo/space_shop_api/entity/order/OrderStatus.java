package com.melo.space_shop_api.entity.order;

public enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELED
}
