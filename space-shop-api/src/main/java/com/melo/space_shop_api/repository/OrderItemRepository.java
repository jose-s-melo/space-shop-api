package com.melo.space_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.space_shop_api.entity.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
