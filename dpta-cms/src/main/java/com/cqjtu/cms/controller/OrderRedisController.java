package com.cqjtu.cms.controller;

import com.alibaba.fastjson.JSON;
import com.cqjtu.cms.entity.Order;
import com.cqjtu.dpta.common.result.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;

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

}
