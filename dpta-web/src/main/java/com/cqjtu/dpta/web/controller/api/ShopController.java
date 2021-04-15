package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.ShopService;
import com.cqjtu.dpta.dao.entity.Shop;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商铺表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/shop")
public class ShopController {

    @Resource
    private ShopService shopService;

    @GetMapping
    public IPage<Shop> page(@PageableDefault Pageable pageable) {
        return shopService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Shop shop) {
        boolean result = shopService.updateById(shop);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Shop shop) {
        boolean result = shopService.save(shop);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = shopService.removeByIds(ids);
        return Result.judge(result);
    }

}
