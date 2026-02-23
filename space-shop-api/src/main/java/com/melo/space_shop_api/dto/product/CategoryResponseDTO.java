package com.melo.space_shop_api.dto.product;

import com.melo.space_shop_api.entity.product.CategoryEnum;

public record CategoryResponseDTO(
    Long id,
    CategoryEnum type,
    String description
) {}
