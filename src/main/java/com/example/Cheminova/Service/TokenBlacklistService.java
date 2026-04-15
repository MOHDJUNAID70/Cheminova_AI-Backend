package com.example.Cheminova.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Transactional
    public void blacklistToken(String token, long expirationTime) {
        redisTemplate.opsForValue().set(
                "blacklist:"+token,
                "true",
                expirationTime,
                TimeUnit.MILLISECONDS
        );
    }

    public boolean IsTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(
                redisTemplate.hasKey("blacklist:"+token)
        );
    }
}
