package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.web.security.CheckParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
@RestController
@RequestMapping("/platform/api/order")
public class PlatformOrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderDService orderDService;

    @GetMapping("full/{orderId}")
    public Result<OrderDto> full(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.getFullOrderDto(orderId);
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

    /**
     * 模糊搜索
     *
     * @param pageable
     * @param keyword  关键字
     * @param option   按条件搜索：0是商品名称，1是商铺名称，2是状态，3是分析商名称
     * @return
     */
    @GetMapping("search")
    public Result search(@PageableDefault Pageable pageable,
                         @RequestParam("keyword") String keyword,
                         @RequestParam(value = "option", required = false, defaultValue = "0") Integer option) {
        // 0到3分别表示按什么搜索
        if (!DptaUtils.in03(option)) {
            return ResultUtils.keyError();
        }
        IPage<Order> page = orderService.search(pageable, keyword, option);
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


    //        @Cacheable(value = "order", key = "'order_page'")
    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<Order> page = orderService.page(pageable);
        return Result.ok(page);
    }

    //    @CacheEvict(value = "order", key = "'order_page'")
    @PostMapping("modif")
    public Result modif(@RequestBody Order order) {
        boolean result = orderService.updateById(order);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Order order) {
        boolean result = orderService.save(order);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = orderService.removeByIds(ids);
        return Result.judge(result);
    }

}
