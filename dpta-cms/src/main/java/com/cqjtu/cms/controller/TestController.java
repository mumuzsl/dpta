package com.cqjtu.cms.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * author: mumu
 * date: 2021/5/11
 */
@RestController
@RequestMapping
public class TestController {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("test")
    public Object test() {
        redisTemplate.opsForValue().set("id", "1", 10, TimeUnit.SECONDS);
        return "ok";
    }
}
