package com.cqjtu.dpta.auto;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import com.cqjtu.dpta.web.controller.api.open.OrderOpenApi;
import com.cqjtu.dpta.web.support.OrderRedisSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * author: mumu
 * date: 2021/5/27
 */
@Slf4j
public class AutoCheck extends OrderRedisSupport implements InitializingBean {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderOpenApi orderOpenApi;

    public void post() {
        log.info("开始过期订单处理");
        help();
        log.info("结束过期订单处理");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                post();
            }
        }).start();
    }

    public void help() {
        LocalDateTime limit = LocalDateTime.now().minusSeconds(OrderRedisSupport.EXPIRE);
        orderService
                .query()
                .lt("DATM", LocalDateTimeUtil.formatNormal(limit))
                .list()
                .forEach(order -> {
                    if (order.getState() == OrderState.WAIT_PAY.state()) {
                        orderOpenApi.overTimeClose(order.getId());
                    }
                });

        orderService
                .query()
                .ge("DATM", LocalDateTimeUtil.formatNormal(limit))
                .eq("STATE", OrderState.WAIT_PAY.state())
                .list()
                .forEach(order -> put(order.getId()));
    }

}
