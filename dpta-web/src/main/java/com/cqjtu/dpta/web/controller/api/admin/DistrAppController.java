package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.DistrAppService;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.dao.entity.DistrApp;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 分销应用表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/platform/api/distr-app")
public class DistrAppController {

    @Resource
    private DistrAppService distrAppService;

    private static final String[] COLUMNS = {"APP_ID"};

    /**
     * 根据分销应用id获取分销应用数据
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result get(@PathVariable Long id) {
        DistrApp distrApp = distrAppService.getById(id);
        if (distrApp == null) {
            return ResultUtils.keyError();
        }
        return Result.ok(distrApp);
    }


    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<DistrApp> page = distrAppService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody DistrApp distrApp) {
        boolean result = distrAppService.updateById(distrApp);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody DistrApp distrApp) {
        boolean result = distrAppService.save(distrApp);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = distrAppService.removeByIds(ids);
        return Result.judge(result);
    }

}
