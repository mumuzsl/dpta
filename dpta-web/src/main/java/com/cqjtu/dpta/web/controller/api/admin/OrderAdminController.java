package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.OrderD;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * author: mumu
 * date: 2021/4/13
 */
@RestController
@RequestMapping("/api/admin/order")
public class OrderAdminController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderDService orderDService;

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

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable Long id,
                         @PageableDefault Pageable pageable) {
        QueryWrapper<OrderD> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", id);
        IPage<OrderD> page = orderDService.page(pageable, queryWrapper);
        return Result.ok(page);
    }


}
