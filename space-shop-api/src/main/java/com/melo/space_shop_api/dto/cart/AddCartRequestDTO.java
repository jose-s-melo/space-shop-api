package com.melo.space_shop_api.dto.cart;

import java.io.Serializable;

public record AddCartRequestDTO(
    Long product,
    Integer quantity
) implements Serializable {}
