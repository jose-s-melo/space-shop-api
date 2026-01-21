package com.melo.space_shop_api.dto.auth;

public record RequestRegisterDTO(
    String name, 
    String email, 
    String password,
    String role
) {}
