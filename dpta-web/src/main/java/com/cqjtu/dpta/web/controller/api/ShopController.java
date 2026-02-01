package com.cqjtu.dpta.web.controller.api;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.Shop;
import com.cqjtu.dpta.service.api.ShopService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商铺表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/distr/api/shop")
public class ShopController {

    @Resource
    private ShopService shopService;

    @GetMapping
    public Result page(@PageableDefault Pageable pageable,
                       Info info) {
        IPage<Shop> page = shopService.lambdaQuery()
                .eq(Shop::getDistrId, info.id())
                .page(shopService.convert(pageable));
        return Result.ok(page);
    }

//    @PostMapping("modif")
//    public Result modif(@RequestBody Shop shop) {
//        boolean result = shopService.updateById(shop);
//        return Result.judge(result);
//    }

    @PostMapping("add")
    public Result add(@RequestBody Shop shop,
                      Info info) {
        shop.setShopId(null);
        shop.setDistrId(info.id());
        if (StrUtil.isBlank(shop.getShopNm())) {
            return Result.fail();
        }
        boolean result = shopService.save(shop);
        return Result.judge(result);
    }

    @GetMapping("{shopId}/comms")
    public Result comms(@PageableDefault Pageable pageable,
                        @PathVariable Long shopId,
                        Info info) {
        IPage<PafComm> page = shopService.getInsellComms(pageable, info.id(), shopId);
        return Result.ok(page);
    }

//    @PostMapping("del")
//    public Result del(@RequestBody List ids) {
//        boolean result = shopService.removeByIds(ids);
//        return Result.judge(result);
//    }

}
