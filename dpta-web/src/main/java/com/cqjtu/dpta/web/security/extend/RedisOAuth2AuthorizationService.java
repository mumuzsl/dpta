package com.cqjtu.dpta.web.security.extend;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author: mumu
 * @date: 2026/1/24 23:33
 */
@Component
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {
    @Resource(name = "authRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;

    public RedisOAuth2AuthorizationService() {
        System.out.println("RedisOAuth2AuthorizationService");
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        if (Objects.nonNull(authorization.getAccessToken())) {
            OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
            Duration between = Duration.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
            redisTemplate.opsForValue().set(buildKey(OAuth2TokenType.ACCESS_TOKEN.getValue(),
                    authorization.getAccessToken().getToken().getTokenValue()), authorization, between);
        }
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        if (Objects.nonNull(authorization.getAccessToken())) {
            OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
            long between = ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
            redisTemplate.delete(buildKey(OAuth2TokenType.ACCESS_TOKEN.getValue(),
                    authorization.getAccessToken().getToken().getTokenValue()));
        }
    }

    public String buildKey(String type, String token) {
        return String.format("%s:%s", type, token);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return null;
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        if (tokenType == OAuth2TokenType.ACCESS_TOKEN) {
            Object o = redisTemplate.opsForValue().get(buildKey(OAuth2TokenType.ACCESS_TOKEN.getValue(), token));
            if (o instanceof OAuth2Authorization authorization) {
                return authorization;
            }
        }
        return null;
    }
}
