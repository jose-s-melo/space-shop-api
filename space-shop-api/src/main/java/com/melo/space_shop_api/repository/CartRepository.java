package com.melo.space_shop_api.repository;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CartRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String buildKey(Long userId) {
        return "cart:" + userId;
    }

    public void addItem(Long userId, Long productId, Integer quantity) {
        String key = buildKey(userId);
        redisTemplate.opsForHash().increment(key, productId.toString(), quantity);
        redisTemplate.expire(key, Duration.ofHours(24));
    }

    public void removeItem(Long userId, Long productId) {
        redisTemplate.opsForHash().delete(buildKey(userId), productId.toString());
    }

}
