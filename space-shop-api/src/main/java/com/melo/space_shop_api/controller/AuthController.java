package com.melo.space_shop_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.space_shop_api.dto.auth.RequestLoginDTO;
import com.melo.space_shop_api.dto.auth.RequestRegisterDTO;
import com.melo.space_shop_api.dto.auth.TokenResponseDTO;
import com.melo.space_shop_api.exception.InvalidUserException;
import com.melo.space_shop_api.service.AuthenticationService;
import com.melo.space_shop_api.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @Autowired 
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody RequestLoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RequestRegisterDTO dto) {
        ResponseEntity<Void> response;
        try {
            userService.register(dto);
            response = ResponseEntity.noContent().build();
        } catch (InvalidUserException e) {
            response = ResponseEntity.badRequest().build();
        }

        return response;
    }
}
