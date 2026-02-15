package com.melo.space_shop_api.entity;

import java.util.List;

public class Cart {

    private User user;
    private List<CartItem> cart;

    public Cart(User user, List<CartItem> cart) {
        this.user = user;
        this.cart = cart;
    }

    public Cart() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }
    
}
