package com.melo.space_shop_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.melo.space_shop_api.dto.auth.RequestLoginDTO;
import com.melo.space_shop_api.dto.auth.TokenResponseDTO;
import com.melo.space_shop_api.entity.user.User;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public TokenResponseDTO login(RequestLoginDTO dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());
        return new TokenResponseDTO(token);
    }

    public UserDetails getPrincipal() {
        UserDetails principal = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            principal = (UserDetails) auth.getPrincipal();
        }

        return principal;
    }

    public User getCurrentUser() {
        User current = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof User) {
            current = (User) auth.getPrincipal();
        }

        return current;
    }
}
