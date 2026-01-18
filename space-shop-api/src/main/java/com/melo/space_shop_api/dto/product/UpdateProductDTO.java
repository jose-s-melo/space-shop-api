package com.melo.space_shop_api.dto.product;

import java.math.BigDecimal;

public record UpdateProductDTO(
    String name,
    BigDecimal price,
    String description
) {}
