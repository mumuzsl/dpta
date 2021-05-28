package com.cqjtu.dpta.web.controller.api;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.api.OrderIndexService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.api.ShopService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.dto.OrderStatisDto;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dao.entity.emus.DeletedEnum;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import com.cqjtu.dpta.dao.repository.OrderIndexRepository;
import com.cqjtu.dpta.web.support.StatisSupport;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/distr/api/order")
public class OrderController extends StatisSupport {

    @Autowired
    private OrderService orderService;
    @Resource
    private OrderIndexService orderIndexService;
    @Resource
    private OrderDService orderDService;
    @Resource
    private ShopService shopService;
    @Resource
    RedisTemplate<String, String> redisTemplate;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private OrderIndexRepository orderIndexRepository;

    @GetMapping("excel")
    public Result excel(HttpSession httpSession) {
        String uuid = IdUtil.simpleUUID();
        httpSession.setAttribute("excel_uuid", uuid);
        return Result.ok(uuid);
    }

    @GetMapping("statis")
    public Result count(@RequestParam(value = "state", required = false) Integer state,
                        @RequestParam(value = "start", required = false) String start,
                        @RequestParam(value = "end", required = false) String end,
                        @RequestParam(value = "shop_id", required = false) Long shopId,
                        @RequestParam(value = "is_state_group", required = false) boolean isStateGroup,
                        @RequestParam(value = "statis_payed", required = false, defaultValue = "false") Boolean statisPayed,
                        Info info) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("state", state);
        params.put("start", start);
        params.put("end", end);
        params.put("shopId", shopId);
        params.put("distrId", info.id());
        params.put("isStateGroup", isStateGroup);
        params.put("deleted", DeletedEnum.NOT_DELETE.value());
        params.put("startState", statisPayed ? OrderState.PAYED.state() : null);
        List<OrderStatisDto> list = orderService.countAndSum(params);

        return Result.ok(list);
    }

    @GetMapping("statis/amount")
    public Result amount(Info info) {
        BoolQueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery("distrId", info.id()))
                .filter(QueryBuilders.rangeQuery("state").gte("1"));

        NativeSearchQuery query = statisQueryBuilder().withQuery(queryBuilder).build();

        SearchHits<MultiGetRequest.Item> hits = elasticsearchOperations.search(query, MultiGetRequest.Item.class, IndexCoordinates.of("order"));

        Map<String, Object> map = new HashMap<>();
        map.put("month", post(hits, "month"));
        map.put("day", post(hits, "day"));

        ParsedSum allAmount = hits.getAggregations().get("all_amount");
        map.put("all", allAmount.getValue());

        return Result.ok(map);
    }


    @GetMapping("statis/recent")
    public Result recent(@RequestParam(name = "day", required = false, defaultValue = "7") Integer day,
                         Info info) {
        NativeSearchQuery query = build(QueryBuilders.matchQuery("distrId", info.id()));
        return recent(day, query);
    }

    @GetMapping("statis/{year}/{month}")
    public Result statisByYearAndMonth(@RequestParam(value = "state", required = false) Integer state,
                                       @RequestParam(value = "start", required = false) String start,
                                       @RequestParam(value = "end", required = false) String end,
                                       @RequestParam(value = "shop_id", required = false) Long shopId,
                                       @RequestParam(value = "is_state_group", required = false) boolean isStateGroup,
                                       @PathVariable String year,
                                       @PathVariable String month,
                                       Info info) {
        StrUtil.center("month", 2, '0');

        Map<String, Object> params = new HashMap<>(4);
        params.put("state", state);
        params.put("start", start);
        params.put("end", end);
        params.put("shopId", shopId);
        params.put("distrId", info.id());
        params.put("isStateGroup", isStateGroup);
        List<OrderStatisDto> list = orderService.countAndSum(params);
        return Result.ok(list);
    }


    @GetMapping("{orderId}")
    public Result full(@PathVariable Long orderId,
                       Info info) {
        Long distrId = orderService.getDistrId(orderId);
        if (distrId == null || !distrId.equals(info.id())) {
            return Result.fail("没有查询到该订单信息");
        }
        OrderDto orderDto = orderService.getOrderDto(orderId);
        return Result.ok(orderDto);
    }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable Long id) {
        List<OrderD> list = orderDService.lambdaQuery().eq(OrderD::getOrderId, id).list();
        return Result.ok(list);
    }

    /**
     * 支持模糊搜索
     *
     * @param pageable
     * @param keyword  关键字
     * @return
     */
    @Cacheable(cacheNames = "order")
    @GetMapping
    public Result page(@PageableDefault(sort = {"datm"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "state", required = false) Integer state,
                       @RequestParam(value = "states", required = false) List<Integer> states,
                       Info info) {
        IPage<OrderDto> page = null;
        if (StringUtils.isBlank(keyword)) {
            if (states != null && !states.isEmpty()) {
                page = orderService.pageOrderDtoByStates(pageable, info.id(), states);
            } else {
                page = orderService.pageOrderDto(pageable, info.id(), state);
            }
        } else {
            page = orderIndexService.searchByDistr(info.id(), keyword, pageable);
        }

        return Result.ok(page);
    }
}


