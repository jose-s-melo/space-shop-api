package com.melo.space_shop_api.entity.cart;

import java.math.BigDecimal;

public class CartItem {

    private Long productId;
    private BigDecimal price; 
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Long productId, BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getGT() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    
    
}
