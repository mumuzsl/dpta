package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.CreditDService;
import com.cqjtu.dpta.api.CreditService;
import com.cqjtu.dpta.api.DealService;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.dao.entity.Deal;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 授信表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/credit")
public class CreditController {

    @Resource
    private CreditService creditService;

    @Resource
    private DealService dealService;

    @Resource
    private CreditDService creditDService;

    private static final String[] COLUMNS = {"SUPP_ID", "DISTR_ID", "STATE","CREDIT_ID"};

    @GetMapping("detail/{id}")
    public Result detail(@PageableDefault Pageable pageable,
                         @PathVariable Long id) {
        Credit credit = creditService.getById(id);
        if (credit == null) {
            return ResultUtils.keyError();
        }
        QueryWrapper<CreditD> queryWrapper = new QueryWrapper();
        queryWrapper.eq(COLUMNS[3], id);
        IPage<CreditD> page = creditDService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @GetMapping("deal/{id}")
    public Result deal(@PageableDefault Pageable pageable,
                       @PathVariable Long id) {
        Credit credit = creditService.getById(id);
        if (credit == null) {
            return ResultUtils.keyError();
        }
        IPage<Deal> page = dealService.pageBySuppAndDistr(pageable, credit.getSuppId(), credit.getDistrId());
        return Result.ok(page);
    }

    @GetMapping("state/{id}/{state}")
    public Result state(@PathVariable Long id,
                        @PathVariable Integer state) {
        Credit credit = creditService.getById(id);
        if (DptaUtils.in03(state)) {
            return ResultUtils.keyError();
        }
        credit.setState(state);
        boolean b = creditService.updateById(credit);
        return Result.ok(b);
    }

    @GetMapping("applied/search")
    public Result applied(@PageableDefault Pageable pageable,
                          @RequestParam("keyword") String keyword,
                          @RequestParam(value = "option", required = false, defaultValue = "0") Integer option) {
        if (DptaUtils.in01(option)) {
            return ResultUtils.keyError();
        }
        IPage<Credit> page = null;
        if (option == 0) {
            page = creditService.applyBySuppNm(pageable, keyword, 1);
        } else {
            page = creditService.applyByDistrNm(pageable, keyword, 1);
        }
        return Result.ok(page);
    }

    @GetMapping("apply/search")
    public Result apply(@PageableDefault Pageable pageable,
                        @RequestParam("keyword") String keyword) {
        IPage<Credit> page = creditService.applyBySuppNm(pageable, keyword, 0);
        return Result.ok(page);
    }

    @GetMapping("apply")
    public Result apply(@PageableDefault Pageable pageable) {
        return byState(pageable, 0);
    }

    @GetMapping("applied")
    public Result applied(@PageableDefault Pageable pageable) {
        return byState(pageable, 1);
    }

    public Result byState(Pageable pageable, Integer state) {
        QueryWrapper<Credit> queryWrapper = new QueryWrapper<Credit>();
        queryWrapper.like(COLUMNS[2], state);
        IPage<Credit> page = creditService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @GetMapping
    public IPage<Credit> page(@PageableDefault Pageable pageable) {
        return creditService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Credit credit) {
        boolean result = creditService.updateById(credit);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Credit credit) {
        boolean result = creditService.save(credit);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = creditService.removeByIds(ids);
        return Result.judge(result);
    }

}
