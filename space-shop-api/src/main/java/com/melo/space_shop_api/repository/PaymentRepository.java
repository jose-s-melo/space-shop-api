package com.melo.space_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.space_shop_api.entity.payment.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
