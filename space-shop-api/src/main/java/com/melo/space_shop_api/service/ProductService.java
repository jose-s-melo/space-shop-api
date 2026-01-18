package com.melo.space_shop_api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.product.AddProductDTO;
import com.melo.space_shop_api.dto.product.ProductResponseDTO;
import com.melo.space_shop_api.entity.Product;
import com.melo.space_shop_api.exception.InvalidProductException;
import com.melo.space_shop_api.exception.ProductNotFoundException;
import com.melo.space_shop_api.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponseDTO addProduct(AddProductDTO dto) throws InvalidProductException {

        if (!validateParams(dto)) {
            throw new InvalidProductException("Invalid product field");
        }

        Product product = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .description(dto.description())
                .build();

        repository.save(product);
        return new ProductResponseDTO(dto.name(), dto.price(), dto.description());
    }

    public ProductResponseDTO deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ProductNotFoundException("Product not found.");
        }

        repository.deleteById(id);

        return new ProductResponseDTO(optional.get().getName(), optional.get().getPrice(),
                optional.get().getDescription());
    }

    public ProductResponseDTO getProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ProductNotFoundException("Product not found.");
        }

        return new ProductResponseDTO(optional.get().getName(), optional.get().getPrice(),
                optional.get().getDescription());
    }

    private boolean validateParams(AddProductDTO dto) {
        boolean valid = true;
        if (dto.name() == null || dto.name().strip().isEmpty()) {
            valid = false;
        } else if (dto.description() == null || dto.description().strip().isEmpty()) {
            valid = false;
        } else if (dto.price() == null || dto.price().signum() == -1) {
            valid = false;
        }
        return valid;
    }
}
