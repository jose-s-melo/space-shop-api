package com.melo.space_shop_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.space_shop_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);
    void deleteById(Long id);
}
