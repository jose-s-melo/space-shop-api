package com.melo.space_shop_api.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Entity that represents a product.
 * 
 * <p>
 * This class maps the products table in the database.
 * </p>
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Positive
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotBlank
    @Column(name = "description", nullable = false)
    @Size(max = 500)
    private String description;

    @NotNull
    @Positive
    @Column(nullable=false)
    private Integer stock;

    @ManyToMany
    @JoinTable(
        name="product_categories",
        joinColumns= @JoinColumn(name="product_id"),
        inverseJoinColumns=@JoinColumn(name="category_id")
    )
    private Set<Category> categories;

    public Product(Long id, @NotBlank String name, @NotBlank BigDecimal price,
            @NotBlank @Size(max = 500) String description, Integer stock) {
        categories = new HashSet<>();
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public Product() {
        categories = new HashSet<>();
    }

    private Product(ProductBuilder builder) {
        categories = new HashSet<>();
        this.name = builder.name;
        this.price = builder.price;
        this.description = builder.description;
        this.stock = builder.stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }


    public static class ProductBuilder {

        private String name;
        private BigDecimal price;
        private String description;
        private Integer stock;

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public Product build() {
            return new Product(this);
        }

    }

}
