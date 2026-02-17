package com.melo.space_shop_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.payment.PaymentAuthorization;
import com.melo.space_shop_api.dto.payment.PaymentMessageDTO;
import com.melo.space_shop_api.dto.payment.PaymentRequestDTO;
import com.melo.space_shop_api.dto.payment.PaymentResponseDTO;
import com.melo.space_shop_api.entity.payment.Payment;
import com.melo.space_shop_api.entity.payment.PaymentMethod;
import com.melo.space_shop_api.entity.payment.PaymentStatus;
import com.melo.space_shop_api.entity.user.User;
import com.melo.space_shop_api.exception.PaymentNotFoundException;
import com.melo.space_shop_api.exception.UserNotFoundException;
import com.melo.space_shop_api.repository.PaymentRepository;
import com.melo.space_shop_api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public PaymentResponseDTO createPayment(PaymentRequestDTO dto) {
        Payment payment = new Payment();
        User user = userRepository.findById(dto.userId()).orElseThrow(() -> new UserNotFoundException());

        payment.setValue(dto.value());
        payment.setUser(user);
        payment.setStatus(PaymentStatus.PENDING);

        Payment saved = paymentRepository.save(payment);
        return new PaymentResponseDTO(saved.getId(), saved.getValue(), saved.getStatus(), saved.getMethod(),
                user.getId());
    }

    @Transactional
    public PaymentMessageDTO pay(Long orderId) {
        Payment payment = getByOrder(orderId);
        PaymentAuthorization authorization = paymentAuthorization();
        PaymentMessageDTO response;

        if (authorization.auth()) {
            payment.setMethod(authorization.method());
            payment.setStatus(PaymentStatus.APPROVED);
            response = new PaymentMessageDTO("Approved");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            response = new PaymentMessageDTO("Failed");
        }

        paymentRepository.save(payment);
        return response;
    }

    public void cancelPayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException());
        payment.setStatus(PaymentStatus.CANCELED);

        paymentRepository.save(payment);
    }

    protected Payment getByOrder(Long orderId) {
        return paymentRepository.findByOrderId(orderId).orElseThrow(() -> new PaymentNotFoundException());
    }

    private PaymentAuthorization paymentAuthorization() {
        return new PaymentAuthorization(true, PaymentMethod.PIX);
    }
}
