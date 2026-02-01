package com.cqjtu.dpta.web.controller.api.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dto.OrderDto;
import com.cqjtu.dpta.service.api.OrderDService;
import com.cqjtu.dpta.service.api.OrderIndexService;
import com.cqjtu.dpta.service.api.OrderService;
import com.cqjtu.dpta.web.security.CheckParam;
import com.cqjtu.dpta.web.support.StatisSupport;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
@RestController
@RequestMapping("/platform/api/order")
public class PlatformOrderController extends StatisSupport {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderIndexService orderIndexService;
    @Resource
    private OrderDService orderDService;

    // todo
    // @GetMapping("statis/amount")
    // public Result amount() {
    //     NativeSearchQuery query = statisQueryBuilder().withQuery(queryBuilder).build();
    //
    //     SearchHits<MultiGetRequest.Item> hits = elasticsearchOperations.search(query, MultiGetRequest.Item.class, IndexCoordinates.of("order"));
    //
    //     Map<String, Object> map = new HashMap<>();
    //     map.put("month", post(hits, "month"));
    //     map.put("day", post(hits, "day"));
    //
    //     ParsedSum allAmount = hits.getAggregations().get("all_amount");
    //     map.put("all", allAmount.getValue());
    //
    //     return Result.ok(map);
    // }
    //
    // @GetMapping("statis/count")
    // public Result count() {
    //     NativeSearchQuery query = statisQueryBuilder().build();
    //
    //     SearchHits<MultiGetRequest.Item> hits = elasticsearchOperations.search(query, MultiGetRequest.Item.class, IndexCoordinates.of("order"));
    //     Map<String, Object> map = new HashMap<>();
    //
    //     ParsedDateRange month = hits.getAggregations().get(statisName("month"));
    //     ParsedDateRange day = hits.getAggregations().get(statisName("day"));
    //
    //     map.put("month", month.getBuckets().get(0).getDocCount());
    //     map.put("day", day.getBuckets().get(0).getDocCount());
    //     map.put("all", hits.getTotalHits());
    //
    //     return Result.ok(map);
    // }
    //
    // @GetMapping("statis/date")
    // public Result date() {
    //     return dateQuery(DateQuery.query.build());
    // }
    //
    // @GetMapping("statis/recent")
    // public Result recent(@RequestParam(name = "day", required = false, defaultValue = "7") Integer day) {
    //     return recent(day, build());
    // }


    /**
     * 模糊搜索
     *
     * @param pageable
     * @param keyword  关键字
     * @return
     */
//    @Cacheable(cacheNames = "order")
    @GetMapping
    public Result page(@PageableDefault(sort = {"datm"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "state", required = false) Integer state,
                       @RequestParam(value = "states", required = false) List<Integer> states) {
        IPage<OrderDto> page = null;
        if (StrUtil.isBlank(keyword)) {
            if (states != null && !states.isEmpty()) {
                page = orderService.pageOrderDtoByStatesAll(pageable, null, states, null);
            } else {
                page = orderService.pageOrderDtoAll(pageable, null, state, null);
            }
        } else {
            page = orderIndexService.searchAllOrder(keyword, pageable);
        }

        return Result.ok(page);
    }

    /**
     * 根据订单id获取订单详情
     *
     * @param id
     * @param pageable
     * @return
     */
    @GetMapping("detail/{id}")
    public Result detail(@PathVariable @CheckParam Long id,
                         @PageableDefault Pageable pageable,
                         Object obj) {
        QueryWrapper<OrderD> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", id);
        List<OrderD> list = orderDService.list(queryWrapper);
        return Result.ok(list);
    }

    @GetMapping("{orderId}")
    public Result full(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.getOrderDto(orderId, null);
        return Result.ok(orderDto);
    }

    /**
     * 根据订单id修改订单状态
     *
     * @param id    订单id
     * @param state
     * @return
     */
//    @CacheEvict(value = "order", key = "'order_page'")
    @GetMapping("state/{id}/{state}")
    public Result state(@PathVariable Long id,
                        @PathVariable Integer state) {
        Order order = orderService.getById(id);
        if (order == null || state < 0 || state > 1) {
            return Result.build(ResultCodeEnum.KEY_ERROR);
        }
        order.setState(state);
        boolean b = orderService.updateById(order);
        return Result.ok(b);
    }
}
