package com.melo.space_shop_api.dto.cart;

public record AddCartRequestDTO(
    Long productId,
    Integer quantity
) {}
