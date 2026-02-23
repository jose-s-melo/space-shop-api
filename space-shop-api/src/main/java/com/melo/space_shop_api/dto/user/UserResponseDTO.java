package com.melo.space_shop_api.dto.user;

import java.io.Serializable;

import com.melo.space_shop_api.entity.user.UserRole;

public record UserResponseDTO(
    Long id,
    String name, 
    String email, 
    UserRole role,
    String cpf,
    String phone
) implements Serializable {}
