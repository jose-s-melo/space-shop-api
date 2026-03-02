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
    private AuthenticationService authService;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * This register an user in the system with that data:
     * {name, cpf, email, role, password and phone}
     */
    public UserResponseDTO register(RequestRegisterDTO dto) throws InvalidUserException {
        if (validateDTO(dto)) {
            User user = null;

            if (authService.getCurrentUser().getRole() != null && authService.getCurrentUser().getRole() == UserRole.ROLE_ADMIN) {
                user = User.builder()
                .name(dto.name())
                .cpf(dto.cpf())
                .email(dto.email())
                .password(encoder.encode(dto.password()))
                .phone(dto.phone())
                .role(dto.role())
                .build();
            } else {
                user = User.builder()
                .name(dto.name())
                .cpf(dto.cpf())
                .email(dto.email())
                .password(encoder.encode(dto.password()))
                .phone(dto.phone())
                .role(UserRole.ROLE_USER)
                .build();
            }

            user = userRepository.save(user);
            return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCpf(), user.getPhone());
        } else {
            throw new InvalidUserException();
        }
    }

    public boolean delete(Long userId) {
        boolean result = false;
        
        if (authService.getCurrentUser().getRole() == UserRole.ROLE_ADMIN) {
            userRepository.deleteById(userId);
            result = true;
        }

        return result;
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

        if (dto.role() == null || dto.role() != UserRole.ROLE_ADMIN || dto.role() != UserRole.ROLE_USER) {
            result = false;
        }

        return result;
    }
}
