package com.cqjtu.dpta.service;

import com.cqjtu.dpta.api.ResveDService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.dao.entity.Resve;
import com.cqjtu.dpta.dao.entity.ResveD;
import com.cqjtu.dpta.dao.mapper.ResveMapper;
import com.cqjtu.dpta.api.ResveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * <p>
 * 预备金表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class ResveServiceImpl extends ServiceImpl<ResveMapper, Resve> implements ResveService {

    @Resource
    ResveDService resveDService;

    @Override
    public Boolean useResve(Long distrId, BigDecimal amount, int type) {
        // 获取该分销商的预备金
        Resve resve = this.getById(distrId);
        LocalDateTime time = LocalDateTime.now();
        // 依据不同的类型对预备金进行增加或减少操作
        if (type == Const.REPAYMENT || type == Const.CASH_OUT) {
            resve.setAmount(resve.getAmount().subtract(amount));
        } else if (type == Const.RECHARGE || type == Const.COMMISSION) {
            resve.setAmount(resve.getAmount().add(amount));
        } else {
            return false;
        }
        resve.setUdtTm(time);
        // 向预备金明细表写入一条记录
        ResveD resveD = new ResveD();
        resveD.setAmount(amount);
        resveD.setDistrId(distrId);
        resveD.setBalance(resve.getAmount());
        resveD.setCreateTm(time);
        resveD.setType(type);
        resveDService.save(resveD);
        // 更新预备金
        this.updateById(resve);
        return true;
    }

    @Override
    public Boolean useResve(Long distrId, BigDecimal amount, int type, Consumer<ResveD> consumer) {
        // 获取该分销商的预备金
        Resve resve = this.getById(distrId);
        LocalDateTime time = LocalDateTime.now();
        // 依据不同的类型对预备金进行增加或减少操作
        if (type == Const.REPAYMENT || type == Const.CASH_OUT) {
            resve.setAmount(resve.getAmount().subtract(amount));
        } else if (type == Const.RECHARGE || type == Const.COMMISSION) {
            resve.setAmount(resve.getAmount().add(amount));
        } else {
            return false;
        }
        resve.setUdtTm(time);
        // 向预备金明细表写入一条记录
        ResveD resveD = new ResveD();
        resveD.setAmount(amount);
        resveD.setDistrId(distrId);
        resveD.setBalance(resve.getAmount());
        resveD.setCreateTm(time);
        resveD.setType(type);
        consumer.accept(resveD);
        resveDService.save(resveD);
        // 更新预备金
        this.updateById(resve);
        return true;
    }

    @Override
    public Boolean useResve(Long distrId, BigDecimal amount, Long dealId) {
        Resve resve = this.getById(distrId);
        LocalDateTime time = LocalDateTime.now();
        resve.setAmount(resve.getAmount().subtract(amount));
        resve.setUdtTm(time);

        ResveD resveD = new ResveD();
        resveD.setAmount(amount);
        resveD.setDistrId(distrId);
        resveD.setBalance(resve.getAmount());
        resveD.setCreateTm(time);
        resveD.setType(Const.PAYMENT);
        resveD.setDealId(dealId);
        resveDService.save(resveD);
        this.updateById(resve);
        return true;
    }
}
