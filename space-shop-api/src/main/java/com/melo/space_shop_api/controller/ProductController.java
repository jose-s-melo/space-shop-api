package com.melo.space_shop_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.space_shop_api.dto.product.AddProductDTO;
import com.melo.space_shop_api.dto.product.ProductResponseDTO;
import com.melo.space_shop_api.exception.InvalidProductException;
import com.melo.space_shop_api.exception.ProductNotFoundException;
import com.melo.space_shop_api.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("products")
@Tag(name = "Products", description = "API endpoint for management of products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all products in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by its id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        ResponseEntity<ProductResponseDTO> response;
        try {
            response = ResponseEntity.ok(service.getProduct(id));
        } catch (ProductNotFoundException e) {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @PostMapping
    @Operation(summary = "Add a new product to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    public ResponseEntity<Void> addProduct(@RequestBody AddProductDTO body) {
        ResponseEntity<Void> response;
        try {
            service.addProduct(body);
            response = ResponseEntity.noContent().build();
        } catch (InvalidProductException e) {
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        ResponseEntity<Void> response;
        try {
            service.deleteProduct(id);
            response = ResponseEntity.ok().build();
        } catch (ProductNotFoundException e) {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid product data"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody AddProductDTO body) {
        ResponseEntity<Void> response;
        try {
            service.updateProduct(id, body);
            response = ResponseEntity.noContent().build();
        } catch (ProductNotFoundException e) {
            response = ResponseEntity.notFound().build();
        } catch (InvalidProductException e) {
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }
}