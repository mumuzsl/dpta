package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.CreditDService;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.dao.mapper.CreditMapper;
import com.cqjtu.dpta.api.CreditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 授信表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class CreditServiceImpl extends ServiceImpl<CreditMapper, Credit> implements CreditService {

    @Resource
    private CreditDService creditDService;

    @Override
    public IPage<Credit> applyBySuppNm(Pageable pageable, String keyword, Integer state) {
        SearchPage<Credit> page = SearchPage.toPage(pageable, keyword);
        return baseMapper.applyBySuppNm(page, state);
    }

    @Override
    public IPage<Credit> applyByDistrNm(Pageable pageable, String keyword, Integer state) {
        SearchPage<Credit> page = SearchPage.toPage(pageable, keyword);
        return baseMapper.applyByDistrNm(page, state);
    }

    /**
     * 使用授信付款
     * @param id: 授信编码
     * @param amount: 使用金额
     * @param dealId: 订单ID
     * @return 成功返回TRUE，失败返回FALSE
     */
    @Override
    public Boolean useCredit(Long id, Double amount, Long dealId) {
        Credit credit = this.getById(id);
        if(credit == null){
            return false;
        }
        BigDecimal usedAmount = credit.getUsedAmout().add(BigDecimal.valueOf(amount));
        CreditD creditD = new CreditD();
        creditD.setCreditId(id);
        creditD.setType(Const.PAYMENT);
        creditD.setAmount(BigDecimal.valueOf(amount));
        creditD.setUsedAmount(usedAmount);
        creditD.setDealId(dealId);
        creditD.setCreateTm(LocalDateTime.now());
        creditDService.addCreditD(creditD);

        credit.setUsedAmout(usedAmount);
        this.updateById(credit);
        return true;
    }

    /**
     * 恢复授信额度
     * @param id: 授信编码
     * @param amount: 恢复的额度
     * @return 成功返回TRUE，失败返回FALSE
     */
    @Override
    public Boolean renewCredit(Long id, Double amount) {
        Credit credit = this.getById(id);
        if(credit == null){
            return false;
        }
        BigDecimal usedAmount = credit.getUsedAmout().subtract(BigDecimal.valueOf(amount));

        CreditD creditD = new CreditD();
        creditD.setCreditId(id);
        creditD.setType(Const.REPAYMENT);
        creditD.setAmount(BigDecimal.valueOf(amount));
        creditD.setUsedAmount(usedAmount);
        creditD.setCreateTm(LocalDateTime.now());
        creditDService.addCreditD(creditD);

        credit.setUsedAmout(usedAmount);
        this.updateById(credit);
        return true;
    }
}
