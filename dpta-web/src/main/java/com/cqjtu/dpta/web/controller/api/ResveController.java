package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.ResveDService;
import com.cqjtu.dpta.api.ResveService;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.entity.Resve;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.ResveD;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 预备金表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/resve")
public class ResveController {

    @Resource
    private ResveService resveService;

    @Resource
    ResveDService resveDService;

    @GetMapping
    public IPage<Resve> page(@PageableDefault Pageable pageable) {
        return resveService.page(pageable);
    }

    /**
     * 根据分销商编码获得分销商的预备金明细
     * @param pageable
     * @param id 分销商编码
     * @return
     */
    @GetMapping("detail/{id}")
    public Result detail(@PageableDefault Pageable pageable,
                         @PathVariable Long id) {
        Resve resve = resveService.getById(id);
        if (resve == null) {
            return ResultUtils.keyError();
        }
        QueryWrapper<ResveD> queryWrapper = new QueryWrapper();
        queryWrapper.eq("DISTR_ID", id);
        IPage<ResveD> page = resveDService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Resve resve) {
        boolean result = resveService.updateById(resve);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Resve resve) {
        boolean result = resveService.save(resve);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = resveService.removeByIds(ids);
        return Result.judge(result);
    }

}
