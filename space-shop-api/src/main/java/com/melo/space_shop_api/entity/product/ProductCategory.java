package com.melo.space_shop_api.entity.product;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_categories")
public class ProductCategory {

    @EmbeddedId
    private ProductCategoryId id;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    @ManyToOne
    @MapsId("categoryId")
    private Category category;

    public ProductCategory() {
    }

    public ProductCategory(Product product, Category category) {
        this.product = product;
        this.category = category;
        this.id = new ProductCategoryId(product.getId(), category.getId());
    }

    public ProductCategoryId getId() {
        return id;
    }

    public void setId(ProductCategoryId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    


}
