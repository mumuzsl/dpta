package com.cqjtu.dpta.common.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis自定义工具类
 */
//@Slf4j
public class RedisUtils {
    private static final long LIMIT = 10000;

    private RedisUtils() {}

    /**
     * 使用{@link RedisWapper}对{@link RedisTemplate}进行封装，并返回{@link RedisWapper}对象。
     *
     * @param redisTemplate
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> RedisWapper<T, E> createWapper(RedisTemplate<T, E> redisTemplate) {
        if (redisTemplate == null) {
            System.out.println("redis template is null");
        }
        RedisWapper<T, E> redisWapper = new RedisWapper<>(redisTemplate);
        if (!redisWapper.ok()) {
//            log.error("redis not able");
        }
        return redisWapper;
    }

    private static void watch() {
//        able.set(false);
        //......
//        able.set(true);
    }
}

