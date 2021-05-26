package com.cqjtu.cms.controller;

import com.alibaba.fastjson.JSON;
import com.cqjtu.cms.entity.Order;
import com.cqjtu.dpta.common.result.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * author: mumu
 * date: 2021/5/2
 */
@RestController
@RequestMapping("/platform/api/order")
public class OrderRedisController {
    private static String uri(String path) {
        return "http://localhost:8080" + path;
    }

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("full/{orderId}")
//    @Cacheable(value = "order", key = "#orderId")
    public Object full(@PathVariable Long orderId) throws IOException {
        String uri = uri("/platform/api/order/full/") + orderId;
        System.out.println("uri: " + uri);
        Result entity = restTemplate.getForObject(uri, Result.class);

        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("order");
        indexRequest.source(JSON.toJSONString(entity.getData()), XContentType.JSON);

        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.getId());
        return entity;
    }

    @PostMapping("date")
    public Object date(@RequestBody Order order) {
        System.out.println(order);
        return order;
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderRedisController.class);

//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//
//    /**
//     * 模拟提交订单
//     *
//     * @return java.lang.Object
//     * @author nxq
//     */
//    @GetMapping("")
//    public Object submit() {
//        String orderId = UUID.randomUUID().toString();
//        logger.info("submit order {}", orderId);
//        this.rabbitTemplate.convertAndSend(
//                RabbitMQConfiguration.orderExchange, //发送至订单交换机
//                RabbitMQConfiguration.routingKeyOrder, //订单定routingKey
//                orderId//订单号   这里可以传对象 比如直接传订单对象
//                , message -> {
//                    // 如果配置了 params.put("x-message-ttl", 5 * 1000);
//                    // 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
//                    message.getMessageProperties().setExpiration(1000 * 10 + "");
//                    return message;
//                });
//
//        return "{'orderId':'" + orderId + "'}";
//    }

}
