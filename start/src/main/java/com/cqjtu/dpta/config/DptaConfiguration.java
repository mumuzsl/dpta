package com.cqjtu.dpta.config;


import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.cqjtu.dpta.web.support.DistrUserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @author mumu
 * @date 2021/3/4 19:28
 */
@Configuration
public class DptaConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());
        return redisTemplate;
    }

//    @Bean
    public FilterRegistrationBean<DistrUserFilter> distrUserFilterFilterRegistrationBean() {
        FilterRegistrationBean<DistrUserFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new DistrUserFilter());
        return bean;
    }
}
