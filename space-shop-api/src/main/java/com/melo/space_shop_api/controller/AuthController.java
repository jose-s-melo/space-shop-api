package com.melo.space_shop_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.space_shop_api.dto.auth.RequestLoginDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid RequestLoginDTO dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register() {
        return ResponseEntity.ok().build();
    }
}
