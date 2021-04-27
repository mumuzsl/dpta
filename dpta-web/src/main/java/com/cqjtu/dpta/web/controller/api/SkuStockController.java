package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.SkuStockService;
import com.cqjtu.dpta.dao.entity.SkuStock;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-23
 */
@RestController
@RequestMapping("/api/data/sku-stock")
public class SkuStockController {

    @Resource
    private SkuStockService skuStockService;

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<SkuStock> page = skuStockService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody SkuStock skuStock) {
        boolean result = skuStockService.updateById(skuStock);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody SkuStock skuStock) {
        boolean result = skuStockService.save(skuStock);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = skuStockService.removeByIds(ids);
        return Result.judge(result);
    }

}
