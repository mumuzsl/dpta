package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.ResveDService;
import com.cqjtu.dpta.dao.entity.ResveD;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 预备金明细表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/resve-d")
public class ResveDController {

    @Resource
    private ResveDService resveDService;

    @GetMapping
    public IPage<ResveD> page(@PageableDefault Pageable pageable) {
        return resveDService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody ResveD resveD) {
        boolean result = resveDService.updateById(resveD);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody ResveD resveD) {
        boolean result = resveDService.save(resveD);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = resveDService.removeByIds(ids);
        return Result.judge(result);
    }

}
