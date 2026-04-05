package com.melo.space_shop_api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.cart.AddCartRequestDTO;
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

    public void addProduct(AddCartRequestDTO dto) {
        User user = authenticationService.getCurrentUser();

        System.out.println(dto);
        System.out.println(dto.product());
        System.out.println(dto.product());

        if (user != null && dto.product() != null) {
            if (!productRepository.existsById(dto.product())) {
                throw new ProductNotFoundException();
            }

            if (dto.quantity() != null && dto.quantity().compareTo(0) > 0) {
                cartRepository.addItem(user.getId(), dto.product(), dto.quantity());
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
