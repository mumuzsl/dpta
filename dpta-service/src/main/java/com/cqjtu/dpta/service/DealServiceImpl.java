package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.*;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.dao.entity.*;
import com.cqjtu.dpta.dao.mapper.DealMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: mumu
 * date: 2021/4/14
 */
@Service
public class DealServiceImpl extends ServiceImpl<DealMapper, Deal> implements DealService {

    @Override
    public IPage<Deal> pageBySuppAndDistr(Pageable pageable, Long suppId, Long distrId) {
        return baseMapper.selectBySuppAndDistr(toPage(pageable), suppId, distrId);
    }

    @Resource
    PafCommService pafCommService;
    @Resource
    CreditService creditService;
    @Resource
    ResveService resveService;

    /**
     * 分销商对订单进行付款
     *
     * @param deal_id 订单编码
     * @return
     */
    @Override
    public PayM payDeal(Long deal_id) {
        Deal deal = baseMapper.selectById(deal_id);
        List<DealD> list = baseMapper.getDealDByDealId(deal_id);
        BigDecimal enCredit = new BigDecimal(0);
        Map<Long,List<DealD>> map = new HashMap<>();
        for (DealD dealD : list) {
            PafComm pafComm = pafCommService.getById(dealD.getCommId());
            Long supp_id = pafComm.getSuppId();
            if (map.containsKey(supp_id)) {
                map.get(supp_id).add(dealD);
            } else {
                List<DealD> list1 = new ArrayList<>();
                list1.add(dealD);
                map.put(supp_id, list1);
            }
        }
        PayM payM = new PayM();
        List<Credit> li = new ArrayList<>();
        for (Long supp_id : map.keySet()) {
            QueryWrapper<Credit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SUPP_ID", supp_id);
            queryWrapper.eq("DISTR_ID", deal.getDistrId());
            queryWrapper.eq("state", Const.ADOPT);
            Credit credit = creditService.getOne(queryWrapper);
            if (credit != null) {
                BigDecimal balance = credit.getCreditAmout().subtract(credit.getUsedAmout());
                List<DealD> list1 = map.get(supp_id);
                BigDecimal pay = new BigDecimal(0);
                for (DealD dealD : list1) {
                    pay = pay.add(dealD.getPrice().multiply(BigDecimal.valueOf(dealD.getCount())));
                }
                if (pay.compareTo(balance) ==- 1) {
                    credit.setEnCredit(pay);
                    enCredit = enCredit.add(pay);
                }
                else {
                    credit.setEnCredit(balance);
                    enCredit = enCredit.add(balance);
                }
                li.add(credit);
            }
        }
        Resve resve = resveService.getById(deal.getDistrId());
        payM.setDealId(deal_id.toString());
        payM.setAmount(deal.getAmount());
        payM.setEnCredit(enCredit);
        payM.setLi(li);
        payM.setResve(resve.getAmount());
        return payM;
    }

    /**
     * 根据订单编码获取订单明细
     *
     * @param deal_id
     * @return
     */
    @Override
    public List<DealD> getDealDByDealId(Long deal_id) {
        return baseMapper.getDealDByDealId(deal_id);
    }
}
