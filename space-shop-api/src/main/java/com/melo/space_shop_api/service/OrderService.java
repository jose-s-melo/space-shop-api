package com.melo.space_shop_api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.payment.PaymentMessageDTO;
import com.melo.space_shop_api.dto.payment.PaymentRequestDTO;
import com.melo.space_shop_api.entity.order.Order;
import com.melo.space_shop_api.entity.order.OrderItem;
import com.melo.space_shop_api.entity.order.OrderStatus;
import com.melo.space_shop_api.entity.payment.PaymentStatus;
import com.melo.space_shop_api.entity.product.Product;
import com.melo.space_shop_api.entity.user.User;
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

    public void createOrder() {
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
        
        paymentService.createPayment(new PaymentRequestDTO(order.getTotal(), user.getId()));
        orderRepository.save(order);
    }

    public PaymentMessageDTO payOrder(Long orderId) {
        return paymentService.pay(orderId);
    }
}
