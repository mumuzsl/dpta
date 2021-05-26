package com.cqjtu.dpta.web.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * author: mumu
 * date: 2021/5/11
 */
//@Component
@Slf4j
public abstract class OrderEventListener extends KeyExpirationEventMessageListener {
//    @Resource
//    private OrderService orderService;
//    @Resource
//    private OrderIndexService orderIndexService;

    public OrderEventListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doHandleMessage(Message message) {
        log.info("处理redis过期事件: key=" + message);
        RedisSupport.consumer(message, id -> handle(id));
        super.doHandleMessage(message);
    }

    public abstract void handle(String id);
}
