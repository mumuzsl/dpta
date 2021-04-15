package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.DistrService;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.api.ShopService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.common.util.Status;
import com.cqjtu.dpta.dao.entity.Distr;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 分销商表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-14
 */
@RestController
@RequestMapping("/api/distr")
public class DistrController {

    @Resource
    private DistrService distrService;

    @Resource
    private ShopService shopService;

    @Resource
    private PafCommService pafCommService;

    private static final String[] COLUMNS = {"DISTR_ID", "STATE"};

    @GetMapping("state/{id}/{state}")
    public Result state(@PathVariable Long id,
                        @PathVariable Integer state) {
        Distr distr = distrService.getById(id);
        if (distr == null) {
            return ResultUtils.keyError();
        }
        boolean ur1 = true;
        boolean ur2 = true;
        if (state == Const.DISABLE) {
            distr.setState(Const.DISABLE);
            UpdateWrapper<Shop> uw1 = new UpdateWrapper<>();
            uw1
                    .eq(COLUMNS[0], id)
                    .set(COLUMNS[1], Const.DISABLE);
            ur1 = shopService.update(uw1);
            UpdateWrapper<PafComm> uw2 = new UpdateWrapper<>();
            uw2
                    .eq(COLUMNS[0], id)
                    .set(COLUMNS[1], Const.OUTSELL);
            ur1 = pafCommService.update(uw2);
        } else {
            distr.setState(Const.ENABLE);
        }
        boolean b = distrService.updateById(distr);
        return Result.ok(b && ur1 && ur2);
    }

    @GetMapping("shop/{id}")
    public Result get(@PageableDefault Pageable pageable,
                      @PathVariable Long id) {
        Distr distr = distrService.getById(id);
        if (distr == null) {
            return ResultUtils.keyError();
        }
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(COLUMNS[0], id);
        IPage<Shop> page = shopService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @GetMapping("{id}")
    public Result get(@PathVariable Long id) {
        Distr distr = distrService.getById(id);
        if (distr == null) {
            return ResultUtils.keyError();
        }
        return Result.ok(distr);
    }

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<Distr> page = distrService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Distr distr) {
        boolean result = distrService.updateById(distr);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Distr distr) {
        boolean result = distrService.save(distr);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = distrService.removeByIds(ids);
        return Result.judge(result);
    }

}
