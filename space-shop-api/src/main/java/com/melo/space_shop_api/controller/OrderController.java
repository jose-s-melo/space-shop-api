package com.melo.space_shop_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.space_shop_api.dto.order.OrderResponseDTO;
import com.melo.space_shop_api.dto.payment.PaymentMessageDTO;
import com.melo.space_shop_api.service.OrderService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder() {
        return ResponseEntity.ok(orderService.createOrder());
    }

    @PostMapping("/{}")
    public ResponseEntity<PaymentMessageDTO> payOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.payOrder(id));
    }
}
