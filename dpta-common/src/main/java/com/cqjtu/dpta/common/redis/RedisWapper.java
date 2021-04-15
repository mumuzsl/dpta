package com.cqjtu.dpta.common.redis;

import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisAccessor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 封装{@link RedisTemplate}，可用于处理RedisTemplate异常。
 * 通常用来处理redis服务不可用的情况。
 *
 * @param <T>
 * @param <E>
 */
public class RedisWapper<T, E> extends RedisTemplate<T, E> {
    private static final long LIMIT = 10000;
    private final AtomicBoolean able = new AtomicBoolean(true);
    private final AtomicLong org = new AtomicLong(System.currentTimeMillis());

    private final RedisTemplate<T, E> template;

    public RedisWapper(RedisTemplate<T, E> template) {
        safe();
        this.template = template;
    }

    /**
     * 检查redis是否可用，或者是否超过了时间限制，超过时间限制，则认为redis是可用的。
     *
     * @return
     */
    private boolean check() {
        if (ok()) {
            return true;
        }

        long cur = System.currentTimeMillis();

        if (org.get() - cur > LIMIT) {
            able.set(true);
            return true;
        }

        return false;
    }

    /**
     * 调用redis的ping函数来判断redis是否可用。如果执行过程发生了异常，则返回false。
     *
     * @return
     */
    private boolean ping() {
        return Optional
                .ofNullable(template)
                .map(RedisAccessor::getConnectionFactory)
                .map(RedisConnectionFactory::getConnection)
                .map(RedisConnectionCommands::ping)
                .map("PONG"::equals)
                .orElse(false);
    }

    /**
     * 判断redis是否可用，发生异常或判断失败，都返回null
     *
     * @return
     */
    private RedisTemplate<T, E> safe() {
        try {
            if (check() && ping()) {
                return template;
            }
        } finally {
            org.set(System.currentTimeMillis());
            able.set(false);
        }
        return null;
    }

    /**
     * 调用该函数执行判断redis是否可用的函数，然后返回自身
     *
     * @return
     */
    public RedisWapper<T, E> multiUse() {
        safe();
        return this;
    }

    /**
     * 该函数接收{@link Function}，当redis可用时返回function执行后的结果，否则返回null
     *
     * @param function
     * @param <O>
     * @return
     */
    public <O> O map(Function<RedisTemplate<T, E>, O> function) {
        return ok() ? function.apply(template) : null;
    }

    /**
     * 该函数接收{@link Consumer},当redis可用时执行Consumer
     *
     * @param consumer
     */
    public void present(Consumer<RedisTemplate<T, E>> consumer) {
        if (ok()) {
            consumer.accept(template);
        }
    }

    /**
     * 获取redis是否可用
     *
     * @return
     */
    public boolean ok() {
        return able.get();
    }

    /**
     * 当redis不可用时，该函数抛出异常，否则返回可用的{@link RedisTemplate}
     *
     * @return
     */
    public RedisTemplate<T, E> use() {
        RedisTemplate<T, E> template = safe();
        if (template == null) {
            throw new RedisDisabledException("redistemplate disabled");
        }
        return template;
    }
}