package com.melo.space_shop_api.dto.auth;

import com.melo.space_shop_api.entity.user.UserRole;
import java.io.Serializable;

public record RequestRegisterDTO(
    String name, 
    String email, 
    String password,
    UserRole role,
    String cpf,
    String phone
) implements Serializable {}
