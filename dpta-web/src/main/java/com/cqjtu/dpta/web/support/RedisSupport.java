package com.cqjtu.dpta.web.support;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * author: mumu
 * date: 2021/5/13
 */
@Component
public class RedisSupport {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    public static final long EXPIRE = 60;

    private static final String DEFAULT_PREFIX = "o_";

    private static String genKey(Object id) {
        return DEFAULT_PREFIX + id;
    }

    public static String getOrderId(String key) {
        return key.substring(DEFAULT_PREFIX.length());
    }

    public static void consumer(Message message, Consumer<String> consumer) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        if (valid(key)) {
            consumer.accept(getOrderId(key));
        }
    }

    public static boolean valid(String key) {
        return StrUtil.startWith(key, DEFAULT_PREFIX);
    }

    public void put(Object id) {
        redisTemplate.opsForValue().set(genKey(id), "NEW_ORDER", EXPIRE, TimeUnit.SECONDS);
    }

    public void remove(Object id) {
        redisTemplate.delete(genKey(id));
    }
}
