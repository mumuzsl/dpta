package com.cqjtu.dpta.web.controller.api.open;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.cqjtu.dpta.api.OrderIndexService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.web.OrderParam;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import com.cqjtu.dpta.web.support.OrderEventListener;
import com.cqjtu.dpta.web.support.OrderRedisSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * author: mumu
 * date: 2021/5/13
 */
@RestController
@RequestMapping("/open/api")
@CacheEvict(cacheNames = "order", allEntries = true)
@Slf4j
public class OrderOpenApi extends OrderEventListener {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderIndexService orderIndexService;
    @Resource
    private OrderRedisSupport orderRedisSupport;

    public OrderOpenApi(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void handle(String id) {
        overTimeClose(Long.valueOf(id));
    }

    public void overTimeClose(Long id) {
        if (orderService.overTimeClose(id) != 0) {
            orderIndexService.updateState(id, OrderState.OVRE_TIME_CLOSE);
            log.info("已取消订单: " + id);
        }
    }

    @PostMapping("close")
    public Result close(@RequestBody Map<String, Object> map) {
        long order_id = DptaUtils.getOrderId(map);
        orderService.handClose(order_id);
        orderIndexService.updateState(order_id, OrderState.OVRE_TIME_CLOSE);

        return Result.ok();
    }

    @PostMapping("pay")
    public Result pay(@RequestBody Map<String, Object> map) {
        long order_id = DptaUtils.getOrderId(map);
        orderService.payOrder(NumberUtil.parseLong(StrUtil.toString(order_id)));
        orderIndexService.updateState(order_id, OrderState.PAYED);
        orderRedisSupport.remove(order_id);
        return Result.ok(order_id);
    }

    @PostMapping("finish")
    public Result finish(@RequestBody Map<String, Object> map) {
        long order_id = DptaUtils.getOrderId(map);
        orderService.success(order_id);
        orderIndexService.updateState(order_id, OrderState.SUCCESS);

        return Result.ok(order_id);
    }

    @PostMapping("refund")
    public Result refund(@RequestBody Map<String, Object> map) {
        long order_id = DptaUtils.getOrderId(map);
        orderService.refund(order_id);
        orderIndexService.updateState(order_id, OrderState.REFUNDING);

        return Result.ok(order_id);
    }

    @PostMapping("create")
    public Result add(@RequestBody OrderParam orderParam) {
        Long id = orderService.create(orderParam);
        orderIndexService.create(orderService.getOrderDto(id, null));
        orderRedisSupport.put(id);
        return Result.ok(id);
    }

    @PostMapping("delete")
    public Result delete(@RequestBody Map<String, Object> map) {
        long order_id = DptaUtils.getOrderId(map);
        orderService.removeById(order_id);
        orderIndexService.LogicDelete(order_id);
        return Result.ok(order_id);
    }

    @PostMapping("refund-finish")
    public Result refundFinish(@RequestBody Map<String, Object> map) {
        long order_id = DptaUtils.getOrderId(map);
        orderService.refundFinish(order_id);
        orderIndexService.updateState(order_id, OrderState.REFUND_CLOSE);
        return Result.ok(order_id);
    }

    @PostMapping("send-comm")
    public Result sendComm(@RequestBody OrderParam orderParam) {
        orderService.sendComm(orderParam);
        orderIndexService.updateState(orderParam.getId(), OrderState.STOCK_FINISH);
        return Result.ok(orderParam.getId());
    }

}
