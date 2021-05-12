package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.DealService;
import com.cqjtu.dpta.dao.entity.Deal;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.PayM;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 交易明细表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/api/deal")
public class DealController {

    @Resource
    private DealService dealService;

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<Deal> page = dealService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Deal deal) {
        boolean result = dealService.updateById(deal);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Deal deal) {
        boolean result = dealService.save(deal);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = dealService.removeByIds(ids);
        return Result.judge(result);
    }

    @GetMapping("getPayM")
    public PayM getPayM (@RequestParam Long dealId) {
        return dealService.payDeal(dealId);
    }

}
