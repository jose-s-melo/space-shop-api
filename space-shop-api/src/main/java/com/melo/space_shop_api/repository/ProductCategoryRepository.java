package com.melo.space_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.space_shop_api.entity.product.ProductCategory;
import com.melo.space_shop_api.entity.product.ProductCategoryId;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId> {

}
