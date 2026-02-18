package com.melo.space_shop_api.dto.order;

public record OrderResponseDTO(
    Long id,
    Long userId,
    Long paymentId
) {}
