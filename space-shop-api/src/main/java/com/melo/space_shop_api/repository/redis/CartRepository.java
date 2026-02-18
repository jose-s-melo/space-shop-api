package com.melo.space_shop_api.repository.redis;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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

    public void clear(Long userId) {
        redisTemplate.opsForHash().delete(buildKey(userId));
    }

    public Map<Long, Integer> getCart(Long userId) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(buildKey(userId));

        Map<Long, Integer> cart = new HashMap<>();

        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            Long productId = Long.valueOf(entry.getKey().toString());
            Integer quantity = Integer.valueOf(entry.getValue().toString());
            cart.put(productId, quantity);
        }

        return cart;
    }
}
