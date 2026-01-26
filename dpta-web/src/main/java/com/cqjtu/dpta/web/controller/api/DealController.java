package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.api.DealService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.dao.entity.Deal;
import com.cqjtu.dpta.dao.entity.DealD;
import com.cqjtu.dpta.dao.entity.PayM;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
    public Result page(@PageableDefault Pageable pageable,
                       Info info) {
        Page<Deal> page = dealService
                .lambdaQuery()
                .eq(Deal::getDistrId, info.id())
                .page(dealService.toPage(pageable));
        return Result.ok(page);
    }


    @GetMapping("{id}")
    public Result detail(@PathVariable Long id) {
        List<DealD> list = dealService.getDealDByDealId(id);
        return Result.ok(list);
    }

    @PostMapping("add")
    public Result add(@RequestBody Deal deal) {
        boolean result = dealService.save(deal);
        return Result.judge(result);
    }

    @GetMapping("getPayM")
    public PayM getPayM(@RequestParam Long dealId) {
        return dealService.payDeal(dealId);
    }

}
