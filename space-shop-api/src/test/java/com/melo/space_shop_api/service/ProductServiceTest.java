package com.melo.space_shop_api.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.melo.space_shop_api.dto.CategoryRequestDTO;
import com.melo.space_shop_api.dto.product.AddProductDTO;
import com.melo.space_shop_api.dto.product.ProductResponseDTO;
import com.melo.space_shop_api.entity.product.Category;
import com.melo.space_shop_api.entity.product.CategoryEnum;
import com.melo.space_shop_api.entity.product.Product;
import com.melo.space_shop_api.exception.InvalidProductException;
import com.melo.space_shop_api.exception.ProductNotFoundException;
import com.melo.space_shop_api.repository.CategoryRepository;
import com.melo.space_shop_api.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService service;

    private final Product defaultProduct = new Product(
            Long.valueOf(1),
            "mouse",
            BigDecimal.valueOf(24.99),
            "good mouse",
            12, "123");

    @Test
    void testAddProductSuccessfully() {
        AddProductDTO dto = new AddProductDTO(
                "test",
                BigDecimal.valueOf(100.00),
                "test", 15, "123");

        Product product = Product.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .sku("123")
                .stock(15)
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
                "             ", -1, null);

        InvalidProductException e = assertThrows(InvalidProductException.class, () -> {
            service.addProduct(dto);
        });

        assertEquals(InvalidProductException.DEFAULT_MESSAGE, e.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void testAddProductWithPriceEqualsZero() {
        AddProductDTO dto = new AddProductDTO(
                "test",
                BigDecimal.valueOf(0.00),
                "test", 1, "test");

        InvalidProductException e = assertThrows(InvalidProductException.class, () -> {
            service.addProduct(dto);
        });

        verify(repository, times(0)).save(any(Product.class));
    }

    @Test
    void testAddProductWithPriceLessThanZero() {
        AddProductDTO dto = new AddProductDTO(
                "test",
                BigDecimal.valueOf(-10.00),
                "test", 1, "test");

        assertThrows(InvalidProductException.class, () -> {
            service.addProduct(dto);
        });

        verify(repository, times(0)).save(any(Product.class));
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

        Exception e = assertThrows(ProductNotFoundException.class, () -> {
            service.deleteProduct(Long.valueOf(1));
        });

        assertEquals(ProductNotFoundException.DEFAULT_MESSAGE, e.getMessage());

        verify(repository, never()).deleteById(any());
    }

    @Test
    void testGetProductSuccessfully() {

        when(repository.findById(Long.valueOf(1))).thenReturn(Optional.of(defaultProduct));

        ProductResponseDTO response = service.deleteProduct(defaultProduct.getId());

        assertEquals("mouse", response.name());

        verify(repository, times(1)).findById(defaultProduct.getId());
    }

    @Test
    void testGetProductNotFound() {

        when(repository.findById(Long.valueOf(1))).thenReturn(Optional.empty());

        Exception e = assertThrows(ProductNotFoundException.class, () -> service.getProduct(Long.valueOf(1)));

        assertEquals(ProductNotFoundException.DEFAULT_MESSAGE, e.getMessage());

        verify(repository, times(1)).findById(Long.valueOf(1));
    }

    @Test
    void testUpdateProductUnsuccessfully() {

        when(repository.findById(defaultProduct.getId())).thenReturn(Optional.of(defaultProduct));

        AddProductDTO dto = new AddProductDTO("mouuuuse", null, null, null, null);

        assertThrows(InvalidProductException.class, () -> service.updateProduct(defaultProduct.getId(), dto));

        verify(repository, times(0)).save(defaultProduct);

    }

    @Test
    void testUpdateProductNotFound() {

        when(repository.findById(defaultProduct.getId())).thenReturn(Optional.empty());

        Exception e = assertThrows(ProductNotFoundException.class,
                () -> service.updateProduct(defaultProduct.getId(),
                        new AddProductDTO("mouuuuse", null, null, null, null)));

        assertEquals(ProductNotFoundException.DEFAULT_MESSAGE, e.getMessage());

        verify(repository, times(1)).findById(defaultProduct.getId());
        verify(repository, times(0)).save(any(Product.class));
    }

    @Test
    void testUpdateProductInvalid() {
        when(repository.findById(defaultProduct.getId())).thenReturn(Optional.of(defaultProduct));

        AddProductDTO dto = new AddProductDTO("", BigDecimal.valueOf(-100.00), "             ", -1, null);

        InvalidProductException e = assertThrows(InvalidProductException.class,
                () -> service.updateProduct(defaultProduct.getId(), dto));

        assertEquals(InvalidProductException.DEFAULT_MESSAGE, e.getMessage());

        verify(repository, times(1)).findById(defaultProduct.getId());
        verify(repository, times(0)).save(any(Product.class));
    }

    @Test
    void testUpdateProductWithPriceZero() {
        when(repository.findById(defaultProduct.getId())).thenReturn(Optional.of(defaultProduct));

        AddProductDTO dto = new AddProductDTO(
                "test",
                BigDecimal.valueOf(0.00),
                "test", 15, "123");

        assertThrows(InvalidProductException.class, () -> service.updateProduct(defaultProduct.getId(), dto));

        verify(repository, times(1)).findById(defaultProduct.getId());
        verify(repository, times(0)).save(any(Product.class));
    }

    @Test
    void testUpdateProductWithPriceLessThanZero() {
        when(repository.findById(defaultProduct.getId())).thenReturn(Optional.of(defaultProduct));

        AddProductDTO dto = new AddProductDTO(
                "test",
                BigDecimal.valueOf(-10.00),
                "test", 15, "123");

        assertThrows(InvalidProductException.class, () -> service.updateProduct(defaultProduct.getId(), dto));

        verify(repository, times(1)).findById(defaultProduct.getId());
        verify(repository, times(0)).save(any(Product.class));
    }

    // Tests for Category

    @Test
    void testCreateCategory() {
        CategoryRequestDTO dto = new CategoryRequestDTO(CategoryEnum.ELECTRONICS, "Electronics");

        when(categoryRepository.save(any(Category.class))).thenReturn(new Category(1L, CategoryEnum.ELECTRONICS, "Eletronics"));

        service.createCategory(dto);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

}
