package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqjtu.dpta.api.*;
import com.cqjtu.dpta.api.support.SettleService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.common.vo.SettleMVo;
import com.cqjtu.dpta.dao.entity.*;
import com.cqjtu.dpta.dao.mapper.SettleMMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
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
//    @Scheduled(cron = "0/1 * * * * ? ")
    public Boolean dayVerify() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper();
        queryWrapper.eq("state", Const.PAID);
        // 获取所有已付款的订单
        List<Order> list = orderService.list(queryWrapper);
        for (Order order : list) {
            QueryWrapper<OrderD> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("order_id", order.getId());
            // 获取订单的订单明细
            List<OrderD> list1 = orderDService.list(queryWrapper1);
            boolean flage = true;
            for (OrderD orderD : list1) {
                // 根据退款规则判断是否超过预留时间
                Long com = orderDService.outRefundTime(orderD.getCommId(), order.getDatm());
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
                    // 获取绑定的佣金规则
                    CommR commR = orderDService.getCommR(orderD.getCommId());
                    PafComm pafComm = pafCommService.getById(orderD.getCommId());
                    base = base.add(pafComm.getSuppPrice().multiply(BigDecimal.valueOf(orderD.getCount())));
                    amount = amount.add(orderD.getPrice().multiply(BigDecimal.valueOf(orderD.getCount())));
                    BigDecimal profit = new BigDecimal(0);
                    if (commR.getType().equals("比例分润")) {
                        profit = orderD.getPrice().subtract(pafComm.getSuppPrice())
                                .multiply(BigDecimal.valueOf(orderD.getCount()))
                                .multiply(commR.getValue());
                    } else if (commR.getType().equals("固定分润")) {
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
            } else {
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

    @Resource
    ResveService resveService;

    /**
     * 月结算
     *
     * @return
     */
    @Override
    @Scheduled(cron = "0 30 23 28 * ? ")
//    @Scheduled(cron = "0/5 * * * * ? ")
    public Boolean MonthSettle() {
        // 获取平台所有的分销商
        List<Distr> list = distrService.list();
        for (Distr distr : list) {
            // 获取该分销商所有处于已核销状态的订单
            List<Order> list1 = orderService.getOrderListByDistrId(distr.getDistrId());
            if (list1.size() == 0) continue;
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
            // 获取分销商等级
            DistrLevel level = distrLevelService.getById(distr.getLevelId());
            // 根据等级规则，控制分润
            profit = profit.multiply(level.getYield());
            // 获取税费计算规则
            TaxR taxR = taxRService.getTaxR(profit);
            // 计算税费
            BigDecimal tax = profit.multiply(taxR.getRate()).subtract(taxR.getDeduction());
            SettleM settleM = new SettleM();
            settleM.setDistrId(distr.getDistrId());
            settleM.setBasP(base);
            settleM.setTaxP(tax);
            settleM.setComP(profit.subtract(tax));
            settleM.setPlaP(amount.subtract(base).subtract(profit));
            settleM.setSettleTm(LocalDateTime.now());
            settleMMapper.insert(settleM);
            resveService.useResve(distr.getDistrId(), base.add(settleM.getComP()), Const.COMMISSION);
            // 分销商提升等级
            distrLevelService.levelUp(distr.getDistrId());
        }
        return true;
    }

    @Override
    public Integer platSum(int d) {
        return settleMMapper.platSum(d);
    }

    @Override
    public PageResult getAll(PageQueryUtil pageUtil) {
        List<SettleMVo> list = settleMMapper.getAll();
        PageResult pageResult = new PageResult(list, list.size(), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public PageResult getByMonth(PageQueryUtil pageUtil, String month) {
        List<SettleMVo> list = settleMMapper.getByMonth(month);
        PageResult pageResult = new PageResult(list, list.size(), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
