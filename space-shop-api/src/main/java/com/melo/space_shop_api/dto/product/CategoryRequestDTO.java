package com.melo.space_shop_api.dto.product;

import java.io.Serializable;

import com.melo.space_shop_api.entity.product.CategoryEnum;

public record CategoryRequestDTO(
    CategoryEnum type,
    String description
) implements Serializable {}
