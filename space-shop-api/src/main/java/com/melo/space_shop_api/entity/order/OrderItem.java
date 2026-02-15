package com.melo.space_shop_api.entity.order;

import java.math.BigDecimal;

import com.melo.space_shop_api.entity.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @Column(nullable=false)
    private Order order;

    @ManyToOne
    @Column(nullable=false)
    private Product product;

    @Column(nullable=false)
    @Positive
    private Integer quantity;

    @Column(nullable=false)
    private BigDecimal priceAtPurchase;

    public OrderItem(Long id, Order order, Product product, @Positive Integer quantity, BigDecimal priceAtPurchase) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public BigDecimal getGT() {
        return priceAtPurchase.multiply(BigDecimal.valueOf(quantity));
    }

}
