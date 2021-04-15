package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping
    public IPage<Order> page(@PageableDefault Pageable pageable) {
        return orderService.page(pageable);
    }

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
