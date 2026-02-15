package com.melo.space_shop_api.entity.cart;

import java.math.BigDecimal;

import com.melo.space_shop_api.entity.product.Product;

public class CartItem {

    private Product product;
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getGT() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
    
}
