package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单明细表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/order-d")
public class OrderDController {

    @Resource
    private OrderDService orderDService;

    @GetMapping
    public IPage<OrderD> page(@PageableDefault Pageable pageable) {
        return orderDService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody OrderD orderD) {
        boolean result = orderDService.updateById(orderD);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody OrderD orderD) {
        boolean result = orderDService.save(orderD);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = orderDService.removeByIds(ids);
        return Result.judge(result);
    }

}
