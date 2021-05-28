package com.cqjtu.dpta.web.support;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * author: mumu
 * date: 2021/5/13
 */
public class OrderRedisSupport {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    public static final long EXPIRE = 24 * 60 * 60;

    public void put(Object id) {
        redisTemplate.opsForValue().set(genKey(id), "NEW_ORDER", EXPIRE, TimeUnit.SECONDS);
    }

    public void remove(Object id) {
        redisTemplate.delete(genKey(id));
    }

    public static String getOrderId(String key) {
        return key.substring(DEFAULT_PREFIX.length());
    }

    private static final String DEFAULT_PREFIX = "o_";

    private static String genKey(Object id) {
        return DEFAULT_PREFIX + id;
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
}
