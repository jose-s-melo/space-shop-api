package com.melo.space_shop_api.entity.product;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductCategoryId implements Serializable {

    private Long productId;

    private Long categoryId;

    public ProductCategoryId() {}

    public ProductCategoryId(Long productId, Long categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryId that = (ProductCategoryId) o;
        return productId.equals(that.productId) && categoryId.equals(that.categoryId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode() + categoryId.hashCode();
    }
    
}