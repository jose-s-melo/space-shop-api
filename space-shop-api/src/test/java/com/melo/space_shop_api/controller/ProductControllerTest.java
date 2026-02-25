package com.melo.space_shop_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melo.space_shop_api.dto.product.AddProductDTO;
import com.melo.space_shop_api.dto.product.ProductResponseDTO;
import com.melo.space_shop_api.repository.UserRepository;
import com.melo.space_shop_api.security.SecurityConfig;
import com.melo.space_shop_api.service.ProductService;
import com.melo.space_shop_api.service.TokenService;

@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private TokenService tokenService;

    //@MockitoBean
    //private AuthenticationService authService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void addProduct_shouldReturn204WhenValid() throws Exception {
        AddProductDTO body = new AddProductDTO("mouse", BigDecimal.valueOf(19.99), "good mouse", 15, "mouse-15");
        when(productService.addProduct(any(AddProductDTO.class))).thenReturn(any(ProductResponseDTO.class));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void addProduct_shouldReturn403() throws Exception {
        AddProductDTO body = new AddProductDTO("mouse", BigDecimal.valueOf(19.99), "good mouse", 15, "mouse-15");
        when(productService.addProduct(any(AddProductDTO.class))).thenReturn(new ProductResponseDTO(Long.valueOf(1), body.name(), body.price(), body.description(), body.stock(), body.sku()));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isForbidden());
    }

    
}
