package com.melo.space_shop_api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melo.space_shop_api.entity.user.User;
import com.melo.space_shop_api.exception.ProductNotFoundException;
import com.melo.space_shop_api.repository.ProductRepository;
import com.melo.space_shop_api.repository.redis.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public void addProduct(Long productId, Integer quantity) {
        User user = authenticationService.getCurrentUser();

        if (user != null && productId != null) {
            if (!productRepository.existsById(productId)) {
                throw new ProductNotFoundException();
            }

            if (quantity != null && quantity.compareTo(0) > 0) {
                cartRepository.addItem(user.getId(), productId, quantity);
            } else {
                throw new IllegalArgumentException("Quantity cannot be negative or zero");
            }
        } else {
            throw new NullPointerException("Product or user cannot be null");
        }
    }

    public void removeProduct(Long productId) {
        User user = authenticationService.getCurrentUser();

        if (user != null && productId != null) {
            cartRepository.removeItem(user.getId(), productId);
        } else {
            throw new NullPointerException("Product or user cannot be null");
        }
    }

    public void clearCart() {
        User user = authenticationService.getCurrentUser();

        if (user != null) {
            cartRepository.clear(user.getId());
        } else {
            throw new NullPointerException("User cannot be null");
        }
    }

    public Map<Long, Integer> get() {
        User user = authenticationService.getCurrentUser();

        if (user != null) {
            return cartRepository.getCart(user.getId());
        } else {
            throw new NullPointerException("User cannot be null");
        }
    }

}
