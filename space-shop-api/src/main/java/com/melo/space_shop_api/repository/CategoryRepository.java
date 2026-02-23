package com.melo.space_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.space_shop_api.entity.product.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
