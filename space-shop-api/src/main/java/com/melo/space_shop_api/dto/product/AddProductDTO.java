package com.melo.space_shop_api.dto.product;

import java.io.Serializable;
import java.math.BigDecimal;

public record AddProductDTO(
    String name, 
    BigDecimal price, 
    String description, 
    Integer stock
) implements Serializable {}
