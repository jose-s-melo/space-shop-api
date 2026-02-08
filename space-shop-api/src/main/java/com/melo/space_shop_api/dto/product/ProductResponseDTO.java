package com.melo.space_shop_api.dto.product;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String name, BigDecimal price, String description) {

}
