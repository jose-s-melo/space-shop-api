package com.melo.space_shop_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.auth.RequestRegisterDTO;
import com.melo.space_shop_api.dto.user.UserResponseDTO;
import com.melo.space_shop_api.entity.user.User;
import com.melo.space_shop_api.entity.user.UserRole;
import com.melo.space_shop_api.exception.InvalidUserException;
import com.melo.space_shop_api.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public UserResponseDTO register(RequestRegisterDTO dto) throws InvalidUserException {
        if (validateDTO(dto)) {
            User user = User.builder()
                            .name(dto.name())
                            .cpf(dto.cpf())
                            .email(dto.email())
                            .password(encoder.encode(dto.password()))
                            .phone(dto.phone())
                            .role(UserRole.ROLE_USER)
                            .build();
    
            User saved = userRepository.save(user);
            return new UserResponseDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getRole(), saved.getCpf(), saved.getPhone());
        } else {
            throw new InvalidUserException();
        }
    }

    private boolean validateDTO(RequestRegisterDTO dto) {
        boolean result = true;

        if (dto.name() == null || dto.name().strip().isBlank()) {
            result = false;
        }

        if (dto.cpf() == null || dto.cpf().strip().isBlank()) {
            result = false;
        }

        if (dto.email() == null || dto.email().strip().isBlank()) {
            result = false;
        }

        if (dto.phone() == null || dto.phone().strip().isBlank()) {
            result = false;
        }

        if (dto.password() == null || dto.password().strip().isBlank()) {
            result = false;
        }

        return result;
    }
}
