package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.dto.OrderDDto;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.common.web.Info;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/distr/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderDService orderDService;

    @GetMapping("{orderId}")
    public Result full(@PathVariable Long orderId,
                       Info info) {
        Long distrId = orderService.getDistrId(orderId);
        if (distrId == null || !distrId.equals(info.longId())) {
            return Result.fail();
        }
        OrderDto orderDto = orderService.getFullOrderDto(orderId);
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
     * @param option   按条件搜索：0是商品名称，1是商铺名称，2是状态
     * @return
     */
    @GetMapping
    public Result page(@PageableDefault Pageable pageable,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "option", required = false, defaultValue = "0") Integer option,
                       Info info) {
        IPage<OrderDto> page = null;
        if (StringUtils.isBlank(keyword)) {
            page = orderService.pageNotDetailOrderDto(pageable, info.longId());
        } else {
            if (!DptaUtils.in02(option)) {
                return ResultUtils.keyError();
            }
            page = orderService.searchByDistrId(pageable, keyword, option, info.longId());
        }
        page.getRecords().forEach(orderDto -> {
            Long id = orderDto.getId();
            List<OrderDDto> fullDetail = orderService.getFullDetail(id);
            orderDto.setDetails(fullDetail);
        });
        return Result.ok(page);
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
