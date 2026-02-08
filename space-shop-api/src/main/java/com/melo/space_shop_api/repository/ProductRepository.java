package com.melo.space_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.space_shop_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
