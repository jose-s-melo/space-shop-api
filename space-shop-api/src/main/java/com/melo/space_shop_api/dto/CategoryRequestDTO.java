package com.melo.space_shop_api.dto;

import java.io.Serializable;

import com.melo.space_shop_api.entity.product.CategoryEnum;

public record CategoryRequestDTO(
    CategoryEnum type,
    String description
) implements Serializable {}
