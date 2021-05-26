package com.cqjtu.dpta.web.controller.api.open;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.*;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.vo.DistrVo;
import com.cqjtu.dpta.dao.entity.*;
import com.cqjtu.dpta.web.support.ControllerUtils;
import com.cqjtu.dpta.web.support.Options;
import com.cqjtu.dpta.web.support.OptionsUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
@RequestMapping("/open/api")
public class DistrOpenApi {

    @Resource
    private DistrAppService distrAppService;
    @Resource
    private DistrLevelService distrLevelService;
    @Resource
    private CreditService creditService;
    @Resource
    private DistrUserService distrUserService;
    @Resource
    private DistrService distrService;
    @Resource
    private ResveService resveService;
    @Resource
    private DealService dealService;

    private static final String[] COLUMNS = {"APP_ID"};

    @Resource
    PasswordEncoder passwordEncoder;
    @PostMapping("distrPay")
    public Boolean distrPay(@RequestBody PayM payM) {
        Long distrId = dealService.getById(payM.getDealId()).getDistrId();
        Distr distr = distrService.getById(distrId);
        if (!passwordEncoder.matches(payM.getPasswd(),distr.getPayPwd())) return false;

        BigDecimal uCredit = payM.getUseCredit();
        if (uCredit.compareTo(new BigDecimal(0))==1) {
            PayM payM1 = dealService.payDeal(Long.valueOf(payM.getDealId()));
            for (Credit credit : payM1.getLi()) {
                if (uCredit.compareTo(new BigDecimal(0))==0) break;
                int f = credit.getEnCredit().compareTo(uCredit);
                if (f <= 0) {
                    Boolean b = creditService.useCredit(credit.getCreditId(),credit.getEnCredit(),Long.valueOf(payM.getDealId()));
                    if (b == true) {
                        uCredit = uCredit.subtract(credit.getEnCredit());
                    }
                    else {
                        return false;
                    }
                }
                if (f > 0) {
                    Boolean b = creditService.useCredit(credit.getCreditId(),uCredit,Long.valueOf(payM.getDealId()));
                    if (b == true) {
                        uCredit = BigDecimal.ZERO;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        if (payM.getUseResve().compareTo(BigDecimal.ZERO)==1) {
            boolean b = resveService.useResve(distrId,payM.getUseResve(),Long.valueOf(payM.getDealId()));
            if (b == true) {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }

    @GetMapping("distr-app")
    public Result distrApp(@RequestParam(value = "keyword", required = false) String keyword,
                           HttpSession httpSession) {
        List<Options<Long>> options = OptionsUtils
                .list(() -> ControllerUtils.quick(distrAppService, keyword, DistrApp::getAppNm),
                        DistrApp::getAppId,
                        DistrApp::getAppNm);
        return Result.ok(options);
    }

    @GetMapping("distr-level")
    public Result distrLevel(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Options<Long>> options = OptionsUtils
                .list(() -> ControllerUtils.quick(distrLevelService, keyword, DistrLevel::getLevelNm),
                        DistrLevel::getLevelId,
                        DistrLevel::getLevelNm);
        return Result.ok(options);
    }

    @GetMapping("supp-credit-distr/{suppId:\\d+}")
    public Result pageBySupp(@PageableDefault Pageable pageable,
                             @PathVariable Long suppId,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        IPage<Distr> page = creditService.pageBySuppAndState(suppId, pageable, keyword, Const.ADOPT);
        return Result.ok(page);
    }

    @GetMapping("distr")
    public Result getDistr(@RequestParam("username") Long username) {
        DistrUser distrUser = distrUserService
                .lambdaQuery()
                .eq(DistrUser::getUsername, username)
                .one();
        Distr distr = distrService
                .lambdaQuery()
                .eq(Distr::getDistrId, distrUser.getDistrId())
                .one();
        DistrVo distrVo = new DistrVo();
        BeanUtils.copyProperties(distr, distrVo);
        return Result.ok(distrVo);
    }
}
