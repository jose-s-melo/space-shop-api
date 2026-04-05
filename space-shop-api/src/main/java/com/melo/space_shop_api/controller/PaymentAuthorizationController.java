package com.melo.space_shop_api.controller;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.space_shop_api.dto.payment.PaymentAuthorizeResponseDTO;
import com.melo.space_shop_api.entity.payment.PaymentMethod;

@RestController
@RequestMapping("/payment-authorize")
public class PaymentAuthorizationController {

    private final Random random = new Random();

    @GetMapping
    public ResponseEntity<PaymentAuthorizeResponseDTO> authorize() {
        int responseCode;
        String message;
        boolean authorize = random.nextBoolean();
        PaymentMethod method = null;
        
        int methodNum = random.nextInt(0, 4);
        switch (methodNum) {
            case 0 -> method = PaymentMethod.PIX;
            case 1 -> method = PaymentMethod.BOLETO;
            case 2 -> method = PaymentMethod.CREDIT_CARD;
            case 3 -> method = PaymentMethod.DEBIT_CARD;
        }
        
        if (authorize) {
            responseCode = 200;
            message = "Success";
        } else {
            responseCode = 401;
            message = "Failed";
            method = PaymentMethod.FAILED;
        }

        return ResponseEntity
                .status(responseCode)
                .body(new PaymentAuthorizeResponseDTO(
                        message,
                        authorize, 
                        method)
                    );
    }
}
