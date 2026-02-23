package com.melo.space_shop_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.CategoryRequestDTO;
import com.melo.space_shop_api.dto.product.AddProductDTO;
import com.melo.space_shop_api.dto.product.CategoryResponseDTO;
import com.melo.space_shop_api.dto.product.ProductResponseDTO;
import com.melo.space_shop_api.dto.product.UpdateProductDTO;
import com.melo.space_shop_api.entity.product.Category;
import com.melo.space_shop_api.entity.product.Product;
import com.melo.space_shop_api.exception.CategoryNotFoundException;
import com.melo.space_shop_api.exception.InvalidCategoryException;
import com.melo.space_shop_api.exception.InvalidProductException;
import com.melo.space_shop_api.exception.ProductNotFoundException;
import com.melo.space_shop_api.repository.CategoryRepository;
import com.melo.space_shop_api.repository.ProductRepository;

@Service
public class ProductService {


    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    /**
     * Adds a new product to the repository after validating the input parameters.
     * The method checks if the product name, description, price, and stock are valid. 
     * If any of the parameters are invalid, it throws an InvalidProductException.
     * @param dto
     * @return ProductResponseDTO containing the details of the added product
     * @throws InvalidProductException if any of the input parameters are invalid
     */
    public ProductResponseDTO addProduct(AddProductDTO dto) throws InvalidProductException {

        if (!validateProductParams(dto)) {
            throw new InvalidProductException();
        }

        Product product = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .description(dto.description())
                .stock(dto.stock())
                .build();

        Product saved = repository.save(product);
        return new ProductResponseDTO(saved.getId(), dto.name(), dto.price(), dto.description(), dto.stock(), dto.sku());
    }

    public ProductResponseDTO deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ProductNotFoundException();
        }

        repository.deleteById(id);

        return new ProductResponseDTO(optional.get().getId(), optional.get().getName(), optional.get().getPrice(),
                optional.get().getDescription(), optional.get().getStock(), optional.get().getSku());
    }

    public ProductResponseDTO getProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return new ProductResponseDTO(optional.get().getId(), optional.get().getName(), optional.get().getPrice(),
                optional.get().getDescription(), optional.get().getStock(), optional.get().getSku());
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
        if (dto.stock() != null && dto.stock().compareTo(0) >= 0) {
            product.setStock(dto.stock());
        }

        repository.save(product);
        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getStock(), product.getSku());
    }

    public List<ProductResponseDTO> getAllProducts() {
        return repository.findAll()
                        .stream()
                        .map(product -> new ProductResponseDTO(
                            product.getId(),
                            product.getName(), 
                            product.getPrice(), 
                            product.getDescription(),
                            product.getStock(),
                            product.getSku()
                        ))
                        .toList();
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        if (validateCategoryParams(dto)) {
            Category category = new Category();
            category.setName(dto.type());
            category.setDescription(dto.description());

            category = categoryRepository.save(category);

            return new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription());
        } else {
            throw new InvalidCategoryException();
        }
    }

    public boolean removeCategory(Long id) {
        boolean result = false;
        if (categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException()) != null) {
            categoryRepository.deleteById(id);
            result = true;
        }
        return result;
    }

    private boolean validateProductParams(AddProductDTO dto) {
        boolean valid = true;
        if (dto.name() == null || dto.name().strip().isEmpty()) {
            valid = false;
        } else if (dto.description() == null || dto.description().strip().isEmpty()) {
            valid = false;
        } else if (dto.price() == null || dto.price().signum() == -1) {
            valid = false;
        } else if (dto.stock() == null || dto.stock().compareTo(0) < 0) {
            valid = false;
        } else if (dto.sku() == null || dto.sku().isBlank()) {
            valid = false;
        } 
        return valid;
    }

    private boolean validateCategoryParams(CategoryRequestDTO dto) {
        boolean valid = true;
        if (dto.type() == null) {
            valid = false;
        }
        if (dto.description() == null || dto.description().isBlank()) {
            valid = false;
        }
        return valid;
    }
}
