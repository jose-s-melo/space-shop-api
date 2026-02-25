package com.melo.space_shop_api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melo.space_shop_api.dto.auth.RequestRegisterDTO;
import com.melo.space_shop_api.dto.user.UserResponseDTO;
import com.melo.space_shop_api.entity.user.User;
import com.melo.space_shop_api.entity.user.UserRole;
import com.melo.space_shop_api.exception.InvalidUserException;
import com.melo.space_shop_api.repository.UserRepository;
import com.melo.space_shop_api.security.SecurityConfig;
import com.melo.space_shop_api.service.AuthenticationService;
import com.melo.space_shop_api.service.TokenService;
import com.melo.space_shop_api.service.UserService;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mcv;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private AuthenticationService authService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean 
    private PasswordEncoder encoder;

    @Test
    @WithMockUser(roles = "ADMIN")
    void registerUser_shouldReturn204() throws JsonProcessingException, Exception {
        RequestRegisterDTO dto = new RequestRegisterDTO("Jose", 
            "jose@example.com", 
            "12345678", 
            UserRole.ROLE_ADMIN, 
            "12345678910", 
            "83988887777");

        when(userService.register(any(RequestRegisterDTO.class))).thenReturn(any(UserResponseDTO.class));

        this.mcv.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    // TODO: pass but its wrong FIX!
    void registerUserWithRoleUser_shouldReturn204() throws JsonProcessingException, Exception {
        RequestRegisterDTO dto = new RequestRegisterDTO("Jose", 
            "jose@example.com", 
            "12345678", 
            UserRole.ROLE_ADMIN, 
            "12345678910", 
            "83988887777");

        when(userRepository.save(any(User.class))).thenReturn(new User(Long.valueOf(1), dto.name(), dto.email(), dto.password(), UserRole.ROLE_USER, dto.cpf(), dto.phone()));
        when(userRepository.findByEmail(dto.email())).thenReturn((UserDetails) new User(Long.valueOf(1), dto.name(), dto.email(), dto.password(), UserRole.ROLE_USER, dto.cpf(), dto.phone()));
        when(userService.register(any(RequestRegisterDTO.class))).thenReturn(new UserResponseDTO(Long.valueOf(1), dto.name(), dto.email(), UserRole.ROLE_USER, dto.cpf(), dto.phone()));


        this.mcv.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isNoContent());

        User saved = (User) userRepository.findByEmail(dto.email());

        assertEquals(UserRole.ROLE_USER, saved.getRole());
    }

    @Test
    @WithMockUser(roles = "USER")
    void registerUserWithInvalidBody_shouldReturn403() throws JsonProcessingException, Exception {
        RequestRegisterDTO dto = new RequestRegisterDTO(null, 
            "jose@example.com", 
            "", 
            UserRole.ROLE_ADMIN, 
            "12345678910", 
            "83988887777");

        when(userService.register(dto)).thenThrow(InvalidUserException.class);

        this.mcv.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isBadRequest());
    }
}
