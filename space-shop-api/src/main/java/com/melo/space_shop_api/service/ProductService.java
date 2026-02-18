package com.melo.space_shop_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.product.AddProductDTO;
import com.melo.space_shop_api.dto.product.ProductResponseDTO;
import com.melo.space_shop_api.dto.product.UpdateProductDTO;
import com.melo.space_shop_api.entity.product.Product;
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
            throw new InvalidProductException();
        }

        Product product = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .description(dto.description())
                .stock(dto.stock())
                .build();

        Product saved = repository.save(product);
        return new ProductResponseDTO(saved.getId(), dto.name(), dto.price(), dto.description(), dto.stock());
    }

    public ProductResponseDTO deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ProductNotFoundException();
        }

        repository.deleteById(id);

        return new ProductResponseDTO(optional.get().getId(), optional.get().getName(), optional.get().getPrice(),
                optional.get().getDescription(), optional.get().getStock());
    }

    public ProductResponseDTO getProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return new ProductResponseDTO(optional.get().getId(), optional.get().getName(), optional.get().getPrice(),
                optional.get().getDescription(), optional.get().getStock());
    }

    public ProductResponseDTO updateProduct(Long id, UpdateProductDTO dto) throws ProductNotFoundException {
        Optional<Product> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ProductNotFoundException();
        }

        Product product = optional.get();

        if (dto.name() != null && !dto.name().strip().isEmpty()) {
            product.setName(dto.name());
        }
        if (dto.description() != null && !dto.description().strip().isEmpty()) {
            product.setDescription(dto.description());
        }
        if (dto.price() != null && dto.price().signum() != -1) {
            product.setPrice(dto.price());
        }
        if (dto.stock() != null && dto.stock().compareTo(Integer.valueOf(0)) >= 0) {
            product.setStock(dto.stock());
        }

        repository.save(product);
        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getStock());
    }

    public List<ProductResponseDTO> getAllProducts() {
        return repository.findAll()
                        .stream()
                        .map(product -> new ProductResponseDTO(
                            product.getId(),
                            product.getName(), 
                            product.getPrice(), 
                            product.getDescription(),
                            product.getStock()
                        ))
                        .toList();
    }

    private boolean validateParams(AddProductDTO dto) {
        boolean valid = true;
        if (dto.name() == null || dto.name().strip().isEmpty()) {
            valid = false;
        } else if (dto.description() == null || dto.description().strip().isEmpty()) {
            valid = false;
        } else if (dto.price() == null || dto.price().signum() == -1) {
            valid = false;
        } else if (dto.stock() == null || dto.stock().compareTo(Integer.valueOf(0)) < 0) {
            valid = false;
        }
        return valid;
    }
}
