package com.cqjtu.dpta.auto;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.vo.CommVo;
import com.cqjtu.dpta.common.web.OrderParam;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import com.cqjtu.dpta.web.controller.api.open.OrderOpenApi;
import com.cqjtu.dpta.web.support.RedisSupport;
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
    private RedisSupport redisSupport;

    private int count = 0;

    private void print(Object id) {
        log.info("自动生成订单: " + id);
    }

    @Scheduled(cron = "0/1 * * * * ?")
    private void testController() {
        try {
            if (count <= 0) {
                Result add = orderOpenApi.add(buildOrderParam(3));
                print(add.getData());
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

    @Scheduled(cron = "0/5 * * * * ?")
    private void payService() {
        Set<String> set = redisTemplate.keys("o_*");
        OrderParam orderParam = new OrderParam();
        orderParam.setExpressNo("YT5388162807091");
        for (String s : set) {
            String ss = StrUtil.split(s, "_")[1];
            Map<String, Object> map = new HashMap<>();
            map.put("order_id", ss);
            orderOpenApi.pay(map);
            log.info("自动支付订单: " + ss);
            if (RandomUtil.randomBoolean()) {
                orderParam.setId(Long.parseLong(ss));
                orderOpenApi.sendComm(orderParam);
                log.info("自动发货: " + ss);
                switch (RandomUtil.randomInt(3)) {
                    case 0:
                        orderOpenApi.finish(map);
                        log.info("自动完成订单: " + ss);
                        break;
                    case 1:
                        orderOpenApi.refund(map);
                        log.info("自动申请退款: " + ss);
                        break;
                    case 2:
                        orderOpenApi.close(map);
                        log.info("自动申请退款: " + ss);
                        break;
                }
            }
            return;
        }
        log.info("没有订单可支付");
    }

//    @Scheduled(cron = "0 0/1 * * * ?")
//    private void testService() {
//        Long id = orderService.create(buildOrderParam(2));
//        print(id);
//    }

    boolean error = true;

    public void help() {
        LocalDateTime limit = LocalDateTime.now().minusSeconds(RedisSupport.EXPIRE);
        List<Order> list = orderService
                .query()
                .gt("DATM", LocalDateTimeUtil.formatNormal(limit))
                .eq("STATE", OrderState.WAIT_PAY.state())
                .list();
        for (Order order : list) {
            redisSupport.put(order.getId());
        }
    }


    private void task() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                help();
                log.info("已将订单重新上传");
            }
        }).start();
    }

    OrderParam buildOrderParam(int dcount) {
        OrderParam vo = new OrderParam();
        vo.setAddress("重庆市南岸区重庆交通大学慧园b栋" + RandomUtil.randomInt(1100, 1160));
        vo.setPhone("1" + RandomUtil.randomNumbers(10));
        vo.setReceiver("用户_" + RandomUtil.randomInt(1000, 10000));

        ArrayList<CommVo> comms = new ArrayList<>(dcount);
        vo.setShopId(3001L + RandomUtil.randomInt(4));

        for (int i = 1; i < dcount; i++) {
            CommVo commVo = new CommVo();
            commVo.setCount(RandomUtil.randomInt(1, 3));
            commVo.setCommId(4001L + RandomUtil.randomInt(3));
//            commVo.setCommId(4002L);
            comms.add(commVo);
        }

        vo.setComms(comms);

        return vo;
    }

}
