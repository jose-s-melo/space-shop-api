package com.melo.space_shop_api.controller;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.space_shop_api.dto.order.OrderDetailResponseDTO;
import com.melo.space_shop_api.dto.order.OrderResponseDTO;
import com.melo.space_shop_api.dto.payment.PaymentMessageDTO;
import com.melo.space_shop_api.service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder() {
        return ResponseEntity.ok(orderService.createOrder());
    }

    @PostMapping("/{id}")
    public ResponseEntity<PaymentMessageDTO> payOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.payOrder(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<Void> sendOrder(@PathVariable Long id) {
        orderService.sendOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/delivered")
    public ResponseEntity<Void> deliveredOrder(@PathVariable Long id, @RequestBody Instant date) {
        orderService.deliveredAt(id, date);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getTotal(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

}
