package com.melo.space_shop_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.melo.space_shop_api.dto.product.AddProductDTO;
import com.melo.space_shop_api.dto.product.ProductResponseDTO;
import com.melo.space_shop_api.entity.Product;
import com.melo.space_shop_api.exception.InvalidProductException;
import com.melo.space_shop_api.exception.ProductNotFoundException;
import com.melo.space_shop_api.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void testAddProductSuccessfully() {
        AddProductDTO dto = new AddProductDTO(
                "test",
                BigDecimal.valueOf(100.00),
                "test");

        Product product = Product.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .build();

        when(repository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO response = service.addProduct(dto);

        assertNotNull(response);
        assertEquals("test", response.name());

        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void testAddInvalidProduct() {
        AddProductDTO dto = new AddProductDTO(
                "",
                BigDecimal.valueOf(-100.00),
                "             ");

        InvalidProductException e = assertThrows(InvalidProductException.class, () -> {
            service.addProduct(dto);
        });

        assertEquals("Invalid product field", e.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void testDeleteProductSuccessfully() {
        Product product = Product.builder()
                .name("test")
                .description("test")
                .price(BigDecimal.valueOf(100.00))
                .build();

        when(repository.findById(Long.valueOf(1))).thenReturn(Optional.of(product));

        ProductResponseDTO response = service.deleteProduct(Long.valueOf(1));

        assertEquals("test", response.name());

        verify(repository).deleteById(Long.valueOf(1));
    }

    @Test
    void testDeleteProductNotFound() {

        when(repository.findById(Long.valueOf(1))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            service.deleteProduct(Long.valueOf(1));
        });

        verify(repository, never()).deleteById(any());
    }

}
