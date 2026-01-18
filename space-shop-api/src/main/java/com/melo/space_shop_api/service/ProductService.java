package com.melo.space_shop_api.service;

import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.AddProductDTO;
import com.melo.space_shop_api.dto.ProductResponseDTO;
import com.melo.space_shop_api.entity.Product;
import com.melo.space_shop_api.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponseDTO addProduct(AddProductDTO dto) {
        Product product = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .description(dto.description())
                .build();

        repository.save(product);
        return new ProductResponseDTO(dto.name(), dto.price(),  dto.description());
    }
}
