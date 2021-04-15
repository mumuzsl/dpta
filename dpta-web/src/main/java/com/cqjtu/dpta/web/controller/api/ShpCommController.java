package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.ShpCommService;
import com.cqjtu.dpta.dao.entity.ShpComm;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商铺-商品表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/shp-comm")
public class ShpCommController {

    @Resource
    private ShpCommService shpCommService;

    @GetMapping
    public IPage<ShpComm> page(@PageableDefault Pageable pageable) {
        return shpCommService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody ShpComm shpComm) {
        boolean result = shpCommService.updateById(shpComm);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody ShpComm shpComm) {
        boolean result = shpCommService.save(shpComm);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = shpCommService.removeByIds(ids);
        return Result.judge(result);
    }

}
