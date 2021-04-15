package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.CommRService;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.common.util.Status;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.PafComm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 佣金规则表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/comm-rule")
public class CommRController {

    @Resource
    private CommRService commRService;

    @Resource
    private PafCommService pafCommService;

    private static final String[] COLUMNS = {"R_COMM_NM", "TYPE", "STATE"};
    private static final Integer INSELL = Status.INSELL.value();

    @GetMapping("insell/{id}")
    public Result insell(@PathVariable Long id) {
        PafComm comm = pafCommService.getById(id);
        if (comm == null) {
            return ResultUtils.keyError();
        }
        comm.setState(INSELL);
        boolean b = pafCommService.updateById(comm);
        return Result.ok(b);
    }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable Long id,
                         @PageableDefault Pageable pageable) {
        CommR commR = commRService.getById(id);
        if (commR == null) {
            return ResultUtils.keyError();
        }
        QueryWrapper<PafComm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("R_COMM_ID", id);
        IPage<PafComm> page = pafCommService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @GetMapping("state/{id}/{state}")
    public Result state(@PathVariable Long id,
                        @PathVariable Integer state) {
        CommR commR = commRService.getById(id);
        if (commR == null || state < 0 || state > 1) {
            return Result.build(ResultCodeEnum.KEY_ERROR);
        }
        boolean updateR = true;
        if (state == 0) {
            commR.setState(0);
            UpdateWrapper<PafComm> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .like("R_COMM_ID", id)
                    .set("STATE", 0);
            updateR = pafCommService.update(updateWrapper);
        } else {
            commR.setState(1);
        }
        boolean b = commRService.updateById(commR);
        return Result.ok(b && updateR);
    }

    @GetMapping("sort")
    public Result sort(@PageableDefault Pageable pageable) {
        IPage<CommR> page = commRService.bindSort(pageable);
        return Result.ok(page);
    }

    @GetMapping("search")
    public Result search(@PageableDefault Pageable pageable,
                         @RequestParam("keyword") String keyword,
                         @RequestParam(value = "option", required = false, defaultValue = "0") Integer option) {
        QueryWrapper<CommR> queryWrapper = new QueryWrapper<>();
        if (!DptaUtils.in02(option)) {
            return ResultUtils.keyError();
        }
        queryWrapper.like(COLUMNS[option], keyword);
        IPage<CommR> page = commRService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @GetMapping("all")
    public Result all() {
        List<CommR> rs = commRService.list();
        return Result.ok(rs);
    }

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<CommR> page = commRService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody CommR commR) {
        boolean result = commRService.updateById(commR);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody CommR commR) {
        boolean result = commRService.save(commR);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = commRService.removeByIds(ids);
        return Result.judge(result);
    }

}
