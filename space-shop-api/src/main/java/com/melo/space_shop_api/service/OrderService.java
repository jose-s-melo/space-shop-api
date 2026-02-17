package com.melo.space_shop_api.service;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.order.OrderResponseDTO;
import com.melo.space_shop_api.dto.payment.PaymentMessageDTO;
import com.melo.space_shop_api.dto.payment.PaymentRequestDTO;
import com.melo.space_shop_api.dto.payment.PaymentResponseDTO;
import com.melo.space_shop_api.entity.order.Order;
import com.melo.space_shop_api.entity.order.OrderItem;
import com.melo.space_shop_api.entity.order.OrderStatus;
import com.melo.space_shop_api.entity.payment.PaymentStatus;
import com.melo.space_shop_api.entity.product.Product;
import com.melo.space_shop_api.entity.user.User;
import com.melo.space_shop_api.exception.OrderNotFoundException;
import com.melo.space_shop_api.exception.ProductNotFoundException;
import com.melo.space_shop_api.repository.OrderItemRepository;
import com.melo.space_shop_api.repository.OrderRepository;
import com.melo.space_shop_api.repository.ProductRepository;
import com.melo.space_shop_api.repository.redis.CartRepository;

@Service
public class OrderService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PaymentService paymentService;

    public OrderResponseDTO createOrder() {
        User user = authenticationService.getCurrentUser();

        Map<Long, Integer> products = cartRepository.getCart(user.getId());

        Order order = new Order();

        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElseThrow(() -> new ProductNotFoundException());
            OrderItem orderItem = new OrderItem();
            
            orderItem.setProduct(product);
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setQuantity(entry.getValue());
            orderItem.setOrder(order);
            
            OrderItem saved = orderItemRepository.save(orderItem);

            order.addItem(saved);
        }

        order.setOrderStatus(OrderStatus.CREATED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setUser(user);
        
        PaymentResponseDTO paymentResponse = paymentService.createPayment(new PaymentRequestDTO(order.getTotal(), user.getId()));
        Order saved = orderRepository.save(order);
        return new OrderResponseDTO(saved.getId(), user.getId(), paymentResponse.id());
    }

    public PaymentMessageDTO payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT);

        PaymentMessageDTO response = paymentService.pay(orderId);
        
        if (response.message().equals("Approved")) {
            order.setOrderStatus(OrderStatus.PAID);
            order.setPaymentStatus(PaymentStatus.APPROVED);
            order.setPaidAt(Instant.now());
        };

        orderRepository.save(order);
        return response;
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    public void sendOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
        if (order.getPaymentStatus() == PaymentStatus.APPROVED) {
            order.setOrderStatus(OrderStatus.SHIPPED);
            order.setShippedAt(Instant.now());
            orderRepository.save(order);
        }
    }
}
