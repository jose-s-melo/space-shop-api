package com.melo.space_shop_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.space_shop_api.dto.auth.RequestLoginDTO;
import com.melo.space_shop_api.dto.auth.RequestRegisterDTO;
import com.melo.space_shop_api.dto.auth.TokenResponseDTO;
import com.melo.space_shop_api.entity.user.User;
import com.melo.space_shop_api.repository.UserRepository;
import com.melo.space_shop_api.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid RequestLoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RequestRegisterDTO dto) {
        ResponseEntity<Void> response;
        
        if (repository.findByEmail(dto.email()) != null) {
            response = ResponseEntity.badRequest().build();
        } else {
            String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
            User newUser = User.builder()
                            .name(dto.name())
                            .email(dto.email())
                            .password(encryptedPassword)
                            .role(dto.role())
                            .build();

            repository.save(newUser);
            response = ResponseEntity.ok().build();
        }
        return response;
    }
}
