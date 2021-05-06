package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.CreditDService;
import com.cqjtu.dpta.api.CreditService;
import com.cqjtu.dpta.api.DealService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.web.support.ControllerUtils;
import com.cqjtu.dpta.common.web.Info;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 授信表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/distr/api/credit")
public class CreditController {

    @Resource
    private CreditService creditService;

    @Resource
    private DealService dealService;

    @Resource
    private CreditDService creditDService;

    private static final String[] COLUMNS = {"SUPP_ID", "DISTR_ID", "STATE", "CREDIT_ID"};

    /**
     * 根据授信id修改状态
     */
    @GetMapping("disable/{id}")
    public Result state(@PathVariable Long id,
                        Info info) {
        Credit credit = creditService.getById(id);
        if (credit == null || credit.getDistrId() != info.longId()) {
            return Result.fail();
        }
        credit.setState(Const.DISABLE);
        boolean b = creditService.updateById(credit);
        return Result.ok(b);
    }

    @GetMapping
    public Result pageByDistr(@PageableDefault Pageable pageable,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              Info info) {
        IPage<Credit> page = ControllerUtils.quick(
                keyword,
                () -> creditService
                        .lambdaQuery()
                        .eq(Credit::getDistrId, info.longId())
                        .page(creditService.toPage(pageable)),
                () -> creditService
                        .pageByDistrAndState(info.longId(), pageable, keyword, Const.ADOPT)
        );
        return Result.ok(page);
    }

//    @PostMapping("modif")
//    public Result modif(@RequestBody Credit credit) {
//        boolean result = creditService.updateById(credit);
//        return Result.judge(result);
//    }
//
//    @PostMapping("add")
//    public Result add(@RequestBody Credit credit) {
//        boolean result = creditService.save(credit);
//        return Result.judge(result);
//    }
//
//    @PostMapping("del")
//    public Result del(@RequestBody List ids) {
//        boolean result = creditService.removeByIds(ids);
//        return Result.judge(result);
//    }

}
