package com.cqjtu.dpta.auto;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.cqjtu.dpta.api.OrderIndexService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.api.VisitsService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.web.CommParam;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import com.cqjtu.dpta.web.controller.api.open.OrderOpenApi;
import com.cqjtu.dpta.web.support.OrderRedisSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: mumu
 * date: 2021/5/11
 */
@Slf4j
public class AutoCreate {
    @Resource
    private OrderService orderService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private OrderOpenApi orderOpenApi;
    @Resource
    private OrderRedisSupport orderRedisSupport;
    @Resource
    private OrderIndexService orderIndexService;
    @Resource
    private VisitsService visitsService;

    private int count = 0;

    private void print(String message) {
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    private void randDatm(Long id) {
        Order order = orderService.getById(id);
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(RandomUtil.randomInt(356));
        order.setDatm(localDateTime);
        orderService.updateById(order);
        orderIndexService.update(id, orderIndex -> orderIndex.setDatm(localDateTime));
    }

    @Scheduled(cron = "0/30 * * * * ?")
//    @Scheduled(cron = "0/1 * * * * ?")
    private void testController() {
        try {
            if (count <= 0) {
                Result<Long> add = orderOpenApi.add(buildOrderParam(3));
                randDatm(add.getData());
                print("自动生成订单: " + add.getData());
                if (error) {
                    task();
                    error = false;
                }
                return;
            }
            count--;
        } catch (Exception e) {
            e.printStackTrace();
            error = true;
            count = 60;
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    private void payService() {
        Set<String> set = redisTemplate.keys("o_*");
        com.cqjtu.dpta.common.web.OrderParam orderParam = new com.cqjtu.dpta.common.web.OrderParam();
        orderParam.setExpressNo("YT5388162807091");
        for (String s : set) {
            String ss = StrUtil.split(s, "_")[1];
            Map<String, Object> map = new HashMap<>();
            map.put("order_id", ss);
            orderOpenApi.pay(map);
            print("自动支付订单: " + ss);
            if (RandomUtil.randomBoolean()) {
                orderParam.setId(Long.parseLong(ss));
                orderOpenApi.sendComm(orderParam);
                print("自动发货: " + ss);
                switch (RandomUtil.randomInt(3)) {
                    case 0:
                        orderOpenApi.finish(map);
                        print("自动完成订单: " + ss);
                        break;
                    case 1:
                        orderOpenApi.refund(map);
                        print("自动申请退款: " + ss);
                        break;
                    case 2:
                        orderOpenApi.close(map);
                        print("自动申请退款: " + ss);
                        break;
                }
            }
            return;
        }
        print("没有订单可支付");
    }

//    @Scheduled(cron = "0 0/1 * * * ?")
//    private void testService() {
//        Long id = orderService.create(buildOrderParam(2));
//        print(id);
//    }

    boolean error = false;

    public void help() {
        LocalDateTime limit = LocalDateTime.now().minusSeconds(OrderRedisSupport.EXPIRE);
        List<Order> list = orderService
                .query()
                .ge("DATM", LocalDateTimeUtil.formatNormal(limit))
                .eq("STATE", OrderState.WAIT_PAY.state())
                .list();
        for (Order order : list) {
            orderRedisSupport.put(order.getId());
        }
    }

    private void task() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                help();
                print("已将订单重新上传");
            }
        }).start();
    }

    com.cqjtu.dpta.common.web.OrderParam buildOrderParam(int dcount) {
        com.cqjtu.dpta.common.web.OrderParam vo = new com.cqjtu.dpta.common.web.OrderParam();
        vo.setAddress("重庆市南岸区重庆交通大学慧园b栋" + RandomUtil.randomInt(1100, 1160));
        vo.setPhone("1" + RandomUtil.randomNumbers(10));
        vo.setReceiver("用户_" + RandomUtil.randomInt(1000, 10000));

        ArrayList<CommParam> comms = new ArrayList<>(dcount);
        vo.setShopId(3001L + RandomUtil.randomInt(4));

        for (int i = 1; i < dcount; i++) {
            CommParam commParam = new CommParam();
            commParam.setCount(RandomUtil.randomInt(1, 3));
            commParam.setCommId(4001L + RandomUtil.randomInt(3));
//            commVo.setCommId(4002L);
            comms.add(commParam);
        }

        vo.setComms(comms);

        visitsService.add(vo.getShopId(), RandomUtil.randomInt(3) == 0 ? null : "user" + RandomUtil.randomInt(100, 999));

        return vo;
    }

}
