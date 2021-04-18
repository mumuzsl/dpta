package com.cqjtu.dpta.web.support;


import com.cqjtu.dpta.common.redis.RedisUtils;
import com.cqjtu.dpta.common.redis.RedisWapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于统计
 *
 * @author mumu
 * @date 2021/3/4 17:51
 */
@Slf4j
public class PvInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private RedisWapper<String, Object> redisWapper = RedisUtils.createWapper(redisTemplate);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        redisWapper.multiUse().map(r -> r.opsForValue().increment("pv"));
        return true;
    }
}
