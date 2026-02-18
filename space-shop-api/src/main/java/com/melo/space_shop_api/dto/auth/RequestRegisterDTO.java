package com.melo.space_shop_api.dto.auth;

import com.melo.space_shop_api.entity.user.UserRole;

public record RequestRegisterDTO(
    String name, 
    String email, 
    String password,
    UserRole role
) {}
