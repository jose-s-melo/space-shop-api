package com.melo.space_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.space_shop_api.entity.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
