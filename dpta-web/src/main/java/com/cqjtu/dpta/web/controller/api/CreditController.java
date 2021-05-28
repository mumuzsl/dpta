package com.cqjtu.dpta.web.controller.api;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.*;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.web.support.ControllerUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

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
    @Resource
    private ResveService resveService;

    private static final String[] COLUMNS = {"SUPP_ID", "DISTR_ID", "STATE", "CREDIT_ID"};

    @GetMapping("refund")
    public Result list(@PageableDefault Pageable pageable,
                       Info info) {
        IPage<CreditD> page = creditDService.pageByDistrAndType(pageable, info.id(), Const.REPAYMENT);
        return Result.ok(page);
    }

    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    DistrService distrService;

    @PostMapping("refund")
    public Result refund(@RequestBody Map<String, String> map,
                         Info info) {
        String pw = map.get("password");
        String credit_id = map.get("credit_id");
        String refund_value = map.get("refund_value");


        if(!passwordEncoder.matches(pw,distrService.getById(info.getId()).getPayPwd()))
            return Result.fail("支付密码错误！");

        BigDecimal refundValue = Optional
                .ofNullable(refund_value)
                .filter(NumberUtil::isNumber)
                .map(NumberUtil::toBigDecimal)
                .orElseThrow(ResultUtils::badRequest);

        Long creditId = Optional
                .ofNullable(credit_id)
                .filter(NumberUtil::isLong)
                .map(NumberUtil::parseLong)
                .map(creditService::getById)
                .filter(c -> c.getDistrId() == info.id())
                .filter(c -> NumberUtil.isGreaterOrEqual(c.getUsedAmout(), refundValue))
                .map(Credit::getCreditId)
                .orElseThrow(ResultUtils::badRequest);

        Boolean b1 = resveService.useResve(info.id(), refundValue, Const.REPAYMENT);
        Boolean b2 = creditService.renewCredit(creditId, refundValue);
        return Result.judge(b1 && b2);
    }

    /**
     * 根据授信id修改状态
     */
    @GetMapping("disable/{id}")
    public Result state(@PathVariable Long id,
                        Info info) {
        Credit credit = creditService.getById(id);
        if (credit == null || credit.getDistrId() != info.id()) {
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
                        .eq(Credit::getDistrId, info.id())
                        .page(creditService.toPage(pageable)),
                () -> creditService
                        .pageByDistrAndState(info.id(), pageable, keyword, Const.ADOPT)
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
