package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqjtu.dpta.api.*;
import com.cqjtu.dpta.api.support.SettleService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.dao.entity.*;
import com.cqjtu.dpta.dao.mapper.SettleMMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.Bidi;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SettleServiceImpl implements SettleService {

    @Resource
    OrderService orderService;

    @Resource
    OrderDService orderDService;

    @Resource
    PafCommService pafCommService;

    /**
     * 日核销
     *
     * @return
     */
    @Override
    @Scheduled(cron = "0 0 23  * * ? ")
    public Boolean dayVerify() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper();
        queryWrapper.eq("state", Const.PAID);
        List<Order> list = orderService.list(queryWrapper);
        for (Order order : list) {
            QueryWrapper<OrderD> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("order_id",order.getId());
            List<OrderD> list1 = orderDService.list(queryWrapper1);
            boolean flage = true;
            for (OrderD orderD : list1) {
//                if (orderD.getState().equals(1)) continue;
                Long com = orderDService.outRefundTime(orderD.getCommId(),order.getDatm());
                if (com == null) {
                    flage = false;
                    break;
                }
            }
            if (flage) {
                BigDecimal sum = new BigDecimal(0);
                BigDecimal base = new BigDecimal(0);
                BigDecimal amount = new BigDecimal(0);
                for (OrderD orderD : list1) {
//                    if (orderD.getState().equals(1)) continue;
                    CommR commR = orderDService.getCommR(orderD.getCommId());
                    PafSkuStock pafSkuStock = pafCommService.getByCommIdAndSkuId(orderD.getCommId(),orderD.getSkuId());
                    base = base.add(pafSkuStock.getSuppPrice());
                    amount = amount.add(orderD.getPrice().multiply(BigDecimal.valueOf(orderD.getCount())));
                    BigDecimal profit = new BigDecimal(0);
                    if (commR.getType().equals("比例分润")) {
                        profit = orderD.getPrice().subtract(pafSkuStock.getSuppPrice())
                                .multiply(BigDecimal.valueOf(orderD.getCount()))
                                .multiply(commR.getValue());
                    }
                    else if (commR.getType().equals("固定分润")) {
                        profit = commR.getValue().multiply(BigDecimal.valueOf(orderD.getCount()));
                    }
                    orderD.setProfit(profit);
                    sum = sum.add(profit);
                    orderDService.updateById(orderD);
                }
                order.setBaseP(base);
                order.setAmount(amount);
                order.setProfit(sum);
                order.setState(Const.VERIFY);
                order.setVerifyTm(LocalDateTime.now());
                orderService.updateById(order);
            }
            else {
                continue;
            }
        }
        return true;
    }

    @Resource
    DistrService distrService;

    @Resource
    DistrLevelService distrLevelService;

    @Resource
    TaxRService taxRService;

    @Resource
    SettleMMapper settleMMapper;
    /**
     * 月结算
     *
     * @return
     */
    @Override
    @Scheduled(cron = "0 30 23 28 * ? ")
    public Boolean MonthSettle() {
        List<Distr> list = distrService.list();
        for (Distr distr : list) {
            List<Order> list1 = orderService.getOrderListByDistrId(distr.getDistrId());
            BigDecimal base = new BigDecimal(0);
            BigDecimal amount = new BigDecimal(0);
            BigDecimal profit = new BigDecimal(0);
            for (Order order : list1) {
                base = base.add(order.getBaseP());
                amount = amount.add(order.getAmount());
                profit = profit.add(order.getProfit());
                order.setState(Const.SETTLE);
                orderService.updateById(order);
            }
            DistrLevel level = distrLevelService.getById(distr.getLevelId());
            profit = profit.multiply(level.getYield());
            TaxR taxR = taxRService.getTaxR(profit);
            BigDecimal tax = profit.multiply(taxR.getRate()).subtract(taxR.getDeduction());
            SettleM settleM = new SettleM();
            settleM.setDistrId(distr.getDistrId());
            settleM.setBasP(base);
            settleM.setTaxP(tax);
            settleM.setComP(profit.subtract(tax));
            settleM.setPlaP(amount.subtract(base).subtract(profit));
            settleM.setSettleTm(LocalDateTime.now());
            settleMMapper.insert(settleM);
        }
        return true;
    }

    @Override
    public Integer platSum(int d) {
        return settleMMapper.platSum(d);
    }
}
