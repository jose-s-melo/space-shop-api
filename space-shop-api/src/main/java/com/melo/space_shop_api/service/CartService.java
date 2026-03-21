package com.melo.space_shop_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melo.space_shop_api.entity.product.Product;
import com.melo.space_shop_api.entity.user.User;
import com.melo.space_shop_api.repository.redis.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public void addProduct(Product product, Integer quantity) {
        User user = authenticationService.getCurrentUser();

        if (user != null && product != null) {
            if (quantity != null && quantity.compareTo(0) > 0) {
                cartRepository.addItem(user.getId(), product.getId(), quantity);
            } else {
                throw new IllegalArgumentException("Quantity cannot be negative or zero");
            }
        } else {
            throw new NullPointerException("Product or user cannot be null");
        }
    }

}
