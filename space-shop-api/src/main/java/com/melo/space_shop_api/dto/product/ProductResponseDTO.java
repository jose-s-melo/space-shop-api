package com.melo.space_shop_api.dto.product;

import java.math.BigDecimal;

public record ProductResponseDTO(String name, BigDecimal price, String description) {

}
