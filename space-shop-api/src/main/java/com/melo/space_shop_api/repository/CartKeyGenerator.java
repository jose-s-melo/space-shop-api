package com.melo.space_shop_api.repository;

import java.lang.reflect.Method;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import com.melo.space_shop_api.service.AuthenticationService;

@Component("cartKeyGenerator")
public class CartKeyGenerator implements KeyGenerator {

    @Autowired
    private AuthenticationService authService;

    @Override
    public Object generate(Object arg0, Method arg1, @Nullable Object... arg2) {
        return "cart:" + authService.getCurrentUser().getId();
    }
}
